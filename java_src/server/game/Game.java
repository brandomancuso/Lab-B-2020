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
    private int numberSession;//in order to count the number of sessions
    private boolean isClosing;

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
        numberSession=1;
        isClosing=false;
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
        try {
            Thread.sleep(200);//to wait for all client they have the gamestub
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        do
        {
            currentSession=new Session(dictionary,persistentSignal,observerClientSet,gameData,numberSession,this);
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
            
            timer.setTime(80);

            if(currentSession.startRealGame(timerThread = new Thread(timer))) 
                return;//to kill the thread
            
            numberSession++;//the counter for the session
            
            timer.setTime(100);
            if(currentSession.startAfterGame(timerThread = new Thread(timer)))
                return;//to kill the thread
            
            //check if another session has to be started          
            findWinner();
        }while (boolNextRound);
        
        //transit every client to winner state
        observerClientSet.forEach((key, value) -> {
            try {
                value.getClientGameStub().changeGameState(3);
            } catch (RemoteException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //send info winner to the client
        sendWinner();
        
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
    
    private void sendWinner()
    {
        //i need only a list of string for the winner
        List<String> winnerNickname=new ArrayList<>(); 
        for(Pair<String,Integer> winner : winners)
        {
            winnerNickname.add(winner.getFirst());//TO:do check better these instructions
        }      
        
        //send the winners with notifyInfoGame()
        observerClientSet.forEach((key, value) -> {
            try {
                value.getClientGameStub().notifyInfoGame(winnerNickname);
            } catch (RemoteException ex) {
                System.err.println(ex);
                observerClientSet.remove(key);
                forcedExit(value.getNickname());//when a client isn't reacheable any more
            }
        });
    }

//public methods for the game 
    
    /**
     * Add a partecipant to the game
     * @param nicknamePlayer nickname of the partecipant
     * @param clientGameStub remote object in order to call its remote methods
     * @return Pair a pair of gamedata (containg all the information of game) and boolean informs whether or not the method works
     * @see Pair
     */
    public Pair<GameData, Boolean> AddPartecipant(String nicknamePlayer, ClientGameStub clientGameStub) {
        Pair<GameData, Boolean> result = null;
        if (gameData.getPlayersList().size() < gameData.getNumPlayers()) {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer, new ObserverClient(nicknamePlayer, clientGameStub, this, timer));
            updateInfoLobby();
            result = new Pair<>(gameData, Boolean.TRUE);//it's not necessary
            if (gameData.getPlayersList().size() == gameData.getNumPlayers()) {
                this.start();
            }
        } else {
            result = new Pair<>(gameData, Boolean.FALSE);
        }
        return result;
    }

    /**
     * in case of an anomalous client system shutdown (also if the user click on X on the upper-right corner of the window) or someone abbandoned
     * @param nickname who abandoned
     */
    public void forcedExit(String nickname) {
        
        persistentSignal.interruptGame();//interrupt the game itself when you are in waiting at state result
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        timerThread.interrupt();//interrupt the timer beacause of game ending
        
        List<String> tmpNickname=new ArrayList<>();
        tmpNickname.add(nickname);//this is the nickname who abandoned
        if (!boolNextRound)
        {
            for(Pair<String,Integer> winner : winners)
            {
                tmpNickname.add(winner.getFirst());//TO:do check better these instructions
            }    
        }

        observerClientSet.forEach((key, value) -> {
            try {
                if (!boolNextRound)
                    value.getClientGameStub().changeGameState(5);//change into abandoned state but with a winner
                else
                    value.getClientGameStub().changeGameState(4);//change into abandoned state
                
                value.getClientGameStub().notifyInfoGame(tmpNickname);
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
    
    /**
     * to save the game and send the result to the server
     */
    public void saveGame ()
    {
        dbReference.updateGame(gameData);
    }
    
    /**
     * get all the information on the Game
     * @return GameData the object containing the information
     * @see GameData
     */
    public GameData getGameData(){
        return this.gameData;
    }
    
    /**
     * set the boolean to enable the next GameSession
     * @param boolNextRound 
     */
    public void setBoolNextRound (Boolean boolNextRound)
    {
        this.boolNextRound=boolNextRound;
    }
    
    /**
     * remove the observerClient by his nickname
     * @param nickname 
     */
    public void removeObserverClient (String nickname)
    {
        observerClientSet.remove(nickname);
    }
    
    
//remote methods for client purpose via RMI
    
    /**
     * request the definition for the specific word
     * @param nickname
     * @param word
     * @return Term containg all the definitions for the word
     * @throws RemoteException 
     * @see Term
     * @see RemoteException
     */
    @Override
    public synchronized Term requestWordDef(String nickname, String word) throws RemoteException {
        Term currentTerm = null;
        try {
            word = word.trim();//to avoid space
            word = word.toLowerCase();//to avoid problem with the dictionary
            currentTerm = dictionary.getTerm(word);
            final WordData wordData = new WordData();
            wordData.setWord(word);
            nickname = nickname.trim();//to avoid problem with the database  
            currentSession.getSessionData().addRequestedWord(nickname, wordData);//add the information in the database
        } catch (InvalidKey ex) {
            //System.err.println(ex);
        }
        return currentTerm;
    }

    /**
     * client could send a ready signal to the game server in order to make the next session start
     * @throws RemoteException 
     * @see RemoteException
     */
    @Override
    public synchronized void ready() throws RemoteException {
        if (++playerReadyNextRound == gameData.getNumPlayers()) {
            timerThread.interrupt();//interupting the time allow the server to go to the next phases
            playerReadyNextRound=0;//to restart the counter
        }
    }

    /**
     * client could tell when want to leave the game
     * @param nickname who leaves the game
     * @throws RemoteException 
     * @see RemoteException
     */
    @Override
    public synchronized void leaveGame(String nickname) throws RemoteException {
        //if the game is in lobby then killing the game but not advice player because there isn't anyone
        if (isLobbyState)
            RemovePartecipant(nickname);
        else
        {
        if(!isClosing)
           {
                isClosing=true;
                persistentSignal.interruptGame();//interrupt the game itself when you are in waiting at state result
                try {
                     Thread.sleep(100);
                   } catch (InterruptedException ex) {
                      Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                timerThread.interrupt();//interrupt the timer beacause of game ending
                //i have to save the game here because the standard execution of the game will be interupted (if there is at least a session played) and send the winner
                if (numberSession > 1)
                {
                     findWinner();
                     saveGame();
                }

                forcedExit(nickname);
                isClosing=false;
           }
        }
    }
    
}
