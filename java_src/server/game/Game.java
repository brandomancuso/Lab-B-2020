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
import utils.Pair;
import server.ServerServiceImpl;

//Coustructor called after a master wants to create a Game
public class Game implements ServerGameStub{
    private GameData gameData;
    private Timer timer;
    private Thread timerThread;
    private Map<String,ObserverClient> observerClientSet;
    private Dictionary dictionary;
    private int playerReadyNextRound;
    private PersistentSignal persistentSignal;//to syncronized the esecution between session and timer
    private ServerServiceImpl serverServiceImpl;//the reference is for call the methods for disconnect and update the player number 
    private Boolean isLobbyState;//to know which method call according to whether or not the game is in lobby frame
    private Boolean boolNextRound;
    private Database dbReference;
    
    public Game (GameData gameData,String hostNickname,ClientGameStub clientGameStub,ServerServiceImpl serverServiceImpl,Database dbReDatabase)
    {
        this.gameData=gameData;
        this.dbReference=dbReDatabase;
        observerClientSet=new HashMap<>();
        persistentSignal=new PersistentSignal();
        timer=new Timer(persistentSignal);
        boolNextRound=true;//i assume that there might be a little succesful at the end of the first round
        isLobbyState=true;//at creation the game is in lobby state
        playerReadyNextRound=0;
        observerClientSet.put(hostNickname,new ObserverClient(hostNickname,clientGameStub,this,timer));
        gameData.addPlayer(hostNickname);
        this.serverServiceImpl=serverServiceImpl;
        loadDictionary();
    }
    
    //private methods for game class purpose
    private void loadDictionary()
    {
        Loader loader=new Loader();
	String file_dizionario= "dict-it.oxt";
	File dizionario=new File(file_dizionario);
        try {
            dictionary=loader.loadDictionaryFromFile(dizionario);
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    private void startSession()
    {
        List<String> winnerNickname=new ArrayList<>();
        int i=1;//in order to count the number of sessions
        while (boolNextRound)
        {
            Session currentSession=new Session(dictionary,persistentSignal,observerClientSet,gameData,i);
            if(isLobbyState)
            {
                gameData.addSession(currentSession.getSessionData());
                timer.setTime(30);
                
                observerClientSet.forEach((key,value)->{
                    try {
                        value.getClientGameStub().changeGameState(0);//change state in waiting inside the lobby
                    } catch (RemoteException ex) {
                        System.err.println(ex);
                    }
                    });
                
                isLobbyState=false;
            }
            //i'm obliged to create a new thread every time beacause there isn't another way to restart the thread but to create a new one from scratch
            currentSession.startBeforeGame(timerThread=new Thread(timer));
            timer.setTime(180);
            currentSession.startRealGame(timerThread=new Thread(timer));
            timer.setTime(180);
            currentSession.startAfterGame(timerThread=new Thread(timer));
            //check if another session has to be started
            for(String nickname : gameData.getPlayersList())
            {
                //create a list of winners
                if(gameData.getPoints(nickname)>=50)
                {
                    winnerNickname.add(nickname);
                    boolNextRound=false;
                }
            }
            
            i++;
        }
        //transit the client to the winner state and send the winners with notifyInfoGame()
        observerClientSet.forEach((key,value)->{
            try {
                value.getClientGameStub().notifyInfoGame(winnerNickname);
                value.getClientGameStub().changeGameState(3);
            } catch (RemoteException ex) {
                System.err.println(ex);
            }
        });
        
        //to save the results of the all sessions
        dbReference.updateGame(gameData); 
    }
    
    private void RemovePartecipant(String nicknamePlayer)
    {     
         if (gameData.getPlayersList().size()-1==0)
         {
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
            serverServiceImpl.updateNumPlayer(gameData.getId());
        }
    }
    
    private void updateInfoLobby()
    {
        observerClientSet.forEach((key,value)->{       
            try {
                value.getClientGameStub().updateLobby(gameData.getPlayersList());//change state in waiting inside the lobby
            } catch (RemoteException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
      
    //public methods for the game creation
    public Pair<GameData,Boolean> AddPartecipant(String nicknamePlayer,ClientGameStub clientGameStub)
    {
        if (gameData.getPlayersList().size()+1>gameData.getNumPlayers())
        {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer,new ObserverClient(nicknamePlayer,clientGameStub,this,timer));
            updateInfoLobby();
            startSession();
            return new Pair<>(gameData,Boolean.FALSE);
        }
        else
        {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer,new ObserverClient(nicknamePlayer,clientGameStub,this,timer));
            updateInfoLobby();
            return new Pair<>(gameData,Boolean.TRUE);
        }
    }
   
    public void exit (String nickname) 
    {
        observerClientSet.clear();//in case of an anomalous client system shutdown (also if the user click on X on the upper-right corner of the window)
        timerThread.interrupt();//interrupt the timer beacause of game ending
        persistentSignal.interruptGame();//interrupt the game itself
        boolNextRound=false;
        
        observerClientSet.forEach((key,value)->{
        try {
                 value.getClientGameStub().changeGameState(4);//change into abandoned state
                 value.getClientGameStub().notifyInfoGame(Arrays.asList(nickname));
            }   catch (RemoteException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
              
        try {
            UnicastRemoteObject.unexportObject(this, false);
            serverServiceImpl.disconnectGame(gameData.getId());
        } catch (NoSuchObjectException ex) {
            System.err.println(ex);
        }
    }
    
    
    //remote methods for client purpose via RMI
    @Override
    public synchronized Term requestWordDef(WordData word) throws RemoteException {
        try {
            return dictionary.getTerm(word.getWord());
        } catch (InvalidKey ex) {
            System.err.println(ex);
        }
        return new Term(word.getWord());
    }

    @Override
    public synchronized void ready(String nickname) throws RemoteException {
        if(++playerReadyNextRound==gameData.getNumPlayers())
            timerThread.interrupt();//interupting the time allow the server to go to the next phases
    }

    @Override
    public synchronized void leaveGame(String nickname) throws RemoteException {     
        if(isLobbyState)
        {
            RemovePartecipant(nickname);
        }
        else
            exit(nickname);   
    }
}