package server.game;

import entity.Term;
import client.ClientGameStub;
import database.Database;
import entity.GameData;
import entity.WordData;
import java.io.File;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import utils.Pair;
import server.ServerServiceImpl;

//Coustructor called after a master wants to create a Game
public class Game extends Thread implements ServerGameStub {

    private GameData gameData;
    private Timer timer;
    private Thread timerThread;
    private Map<String, ObserverClient> observerClientSet;
    private Dictionary dictionary;
    private int playerReadyNextRound;
    private PersistentSignal persistentSignal;//to syncronized the esecution between session and timer
    private ServerServiceImpl serverServiceImpl;//the reference is for call the methods for disconnect and update the player number 
    private Boolean isLobbyState;//to know which method call according to whether or not the game is in lobby frame
    private Boolean boolNextRound;
    private Database dbReference;
    private Session currentSession;
    private List<Pair<String,Integer>> winners;
    private final int WINNING_POINT = 1;

    public Game(GameData gameData, String hostNickname, ClientGameStub clientGameStub, ServerServiceImpl serverServiceImpl, Database dbReDatabase) {
        this.gameData = gameData;
        this.dbReference = dbReDatabase;
        observerClientSet = new HashMap<>();
        persistentSignal = new PersistentSignal(this);
        timer = new Timer(persistentSignal);
        winners=new ArrayList<>();
        boolNextRound = true;//i assume that there might be a little succesful at the end of the first round
        isLobbyState = true;//at creation the game is in lobby state
        playerReadyNextRound = 0;
        observerClientSet.put(hostNickname, new ObserverClient(hostNickname, clientGameStub, this, timer));
        gameData.addPlayer(hostNickname);
        this.serverServiceImpl = serverServiceImpl;
        loadDictionary();
        updateInfoLobby();
    }

    //private methods for game class purpose
    private void loadDictionary() {
        Loader loader = new Loader();
        String file_dizionario = "resources/dict-it.oxt";
        File dizionario = new File(file_dizionario);
        try {
            dictionary = loader.loadDictionaryFromFile(dizionario);
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    public void run()
    {
        int i=0;//in order to count the number of sessions
        do
        {
            i++;//the counter for the session
            currentSession=new Session(dictionary,persistentSignal,observerClientSet,gameData,i,this);
            if(isLobbyState)
            {
                gameData.addSession(currentSession.getSessionData());
                timer.setTime(5);
                
                observerClientSet.forEach((key,value)->{
                    try {
                        value.getClientGameStub().changeGameState(0);//change state in waiting inside the lobby
                    } catch (RemoteException ex) {
                        System.err.println(ex);
                        observerClientSet.remove(key);
                        forcedExit(value.getNickname());//when a client isn't reacheable any more
                    }
                    });
                //i'm obliged to create a new thread every time beacause there isn't another way to restart the thread but to create a new one from scratch
                currentSession.startBeforeGame(timerThread = new Thread(timer));
                isLobbyState=false;
            }
            
            timer.setTime(70);
            currentSession.startRealGame(timerThread = new Thread(timer));
            timer.setTime(100);
            currentSession.startAfterGame(timerThread = new Thread(timer));
            //to save the results of the all sessions and set the winners
            //check if another session has to be started          
            findWinner();
        }while (boolNextRound);
        
        //i need only a list of string for the winner
        List<String> winnerNickname=new ArrayList<>(); 
        for(Pair<String,Integer> winner : winners)
        {
            winnerNickname.add(winner.getFirst());//TO:do check better these instructions
        }      
        
        //transit the client to the winner state and send the winners with notifyInfoGame()
        observerClientSet.forEach((key, value) -> {
            try {
                value.getClientGameStub().changeGameState(3);
                value.getClientGameStub().notifyInfoGame(winnerNickname);
            } catch (RemoteException ex) {
                System.err.println(ex);
                observerClientSet.remove(key);
                forcedExit(value.getNickname());//when a client isn't reacheable any more
            }
        });
        
        
        //save the game
        saveGame();         
        
        //disconnect the game because of the win state
        try {
            serverServiceImpl.disconnectGame(gameData.getId());
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.err.println(ex);
        }
        
    }

    private void RemovePartecipant(String nicknamePlayer) {
        if (gameData.getPlayersList().size() - 1 == 0) {
            gameData.removePlayer(nicknamePlayer);
            observerClientSet.remove(nicknamePlayer);
            try {
                 UnicastRemoteObject.unexportObject(this, false);
             } catch (NoSuchObjectException ex) {
                 Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
             }
            serverServiceImpl.disconnectGame(gameData.getId());
         }
        else
        {
            gameData.removePlayer(nicknamePlayer);
            observerClientSet.remove(nicknamePlayer);
            updateInfoLobby();
            serverServiceImpl.updateNumPlayer();
        }
    }
    
    private void updateInfoLobby()
    {
        observerClientSet.forEach((key,value)->{       
            try {
                value.getClientGameStub().updateLobby(gameData.getPlayersList());//change state in waiting inside the lobby
            } catch (RemoteException ex) {
                observerClientSet.remove(key);
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void findWinner ()
    {
        //search for the winners
         for (String nickname : gameData.getPlayersList()) {
                //create a list of winners
                if (gameData.getPoints(nickname) >= WINNING_POINT) {
                    winners.add(new Pair (nickname,gameData.getPoints(nickname)));
                    boolNextRound = false;
                }
         }
         
        //i order the result by BubbleSort
        for (int i = 0; i < winners.size(); i++) {
            boolean exchange = false;
            for (int j = winners.size() - 1; j > i; j--) {
                if (winners.get(j).getLast().compareTo(winners.get(j-1).getLast()) > 0)
                {
                    Pair<String,Integer> tmpWinners=winners.get(j);
                    winners.set(j,winners.get(j - 1));
                    winners.set(j - 1,tmpWinners);
                    exchange = true;
                }
            }
            if (!exchange) {
                break;
            }
        }
        
        //i remove all the players with less than the maximun player score inside this list, founding the real winners
        winners= winners.parallelStream().filter(winner-> winner.getLast().equals(winners.get(0).getLast())).collect(Collectors.toList());
    }

    //public methods for the game 
    public Pair<GameData, Boolean> AddPartecipant(String nicknamePlayer, ClientGameStub clientGameStub) {
        if (gameData.getPlayersList().size() + 1 == gameData.getNumPlayers()) {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer, new ObserverClient(nicknamePlayer, clientGameStub, this, timer));
            updateInfoLobby();
            this.start();//start the game
            return new Pair<>(gameData, Boolean.FALSE);
        } else {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer, new ObserverClient(nicknamePlayer, clientGameStub, this, timer));
            updateInfoLobby();
            return new Pair<>(gameData, Boolean.TRUE);
        }
    }

    //in case of an anomalous client system shutdown (also if the user click on X on the upper-right corner of the window) or someone abbandoned
    public void forcedExit(String nickname) {
        timerThread.interrupt();//interrupt the timer beacause of game ending
        persistentSignal.interruptGame();//interrupt the game itself when you are in waiting at state result
        boolNextRound = false;

        observerClientSet.forEach((key, value) -> {
            try {
                value.getClientGameStub().changeGameState(4);//change into abandoned state
                value.getClientGameStub().notifyInfoGame(Arrays.asList(nickname));
            } catch (RemoteException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            serverServiceImpl.disconnectGame(gameData.getId());
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            System.err.println(ex);
        }
    }
    
    public void saveGame ()
    {
        dbReference.updateGame(gameData);
    }
    
    public GameData getGameData(){
        return this.gameData;
    }
    
    public void setBoolNextRound (Boolean boolNextRound)
    {
        this.boolNextRound=boolNextRound;
    }
    
    public void removeObserverClient (String nickname)
    {
        observerClientSet.remove(nickname);
    }
    
    
    //remote methods for client purpose via RMI
    @Override
    public synchronized Term requestWordDef(String nickname,String word) throws RemoteException {
        try {
            word=word.trim();//to avoid space
            word=word.toLowerCase();//to avoid problem with the dictionary
            return dictionary.getTerm(word);
        } catch (InvalidKey ex) {
            System.err.println(ex);
        }
        WordData wordData=new WordData();
        wordData.setWord(word);
        currentSession.getSessionData().addRequestedWord(nickname,wordData);//add the information in the database
        return new Term(word);
    }

    @Override
    public synchronized void ready(String nickname) throws RemoteException {
        if (++playerReadyNextRound == gameData.getNumPlayers()) {
            timerThread.interrupt();//interupting the time allow the server to go to the next phases
        }
    }

    @Override
    public synchronized void leaveGame(String nickname) throws RemoteException {
        //if the game is in lobby then killing the game but not advice player because there isn't anyone
        if (isLobbyState)
            RemovePartecipant(nickname);
        else
        {
           //i have to save the game here because the standard execution of the game will be interupted
           saveGame();
           forcedExit(nickname);
        }
    }
    
}
