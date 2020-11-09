package server.Game;

import client.ClientGameStub;
import entity.GameData;
import entity.WordData;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Coustructor called after a master wants to create a Game
public class Game implements ServerGameStub{
    private GameData gameData;
    private Timer timer;
    private Thread timerThread;
    private Map<String,ObserverClient> observerClientSet;
    private Dictionary dictionary;
    private Boolean boolNextRound;
    private int playerReadyNextRound;
    private PersistentSignal persistentSignal;
    
    public Game (GameData gameData,String hostNickname,ClientGameStub clientGameStub)
    {
        this.gameData=gameData;
        observerClientSet=new HashMap<>();
        persistentSignal=new PersistentSignal();
        timer=new Timer(persistentSignal);
        boolNextRound=true;//i assume that there might be a little succesful at the end of the first round
        playerReadyNextRound=0;
        observerClientSet.put(hostNickname,new ObserverClient(hostNickname,clientGameStub,this,timer));
        gameData.addPlayer(hostNickname);
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
        while (boolNextRound)
        {
            Session currentSession=new Session(dictionary,persistentSignal,observerClientSet,gameData);
            gameData.addSession(currentSession.getSessionData());
            timer.setTime(30);
            currentSession.startBeforeGame(timerThread=new Thread(timer));
            timer.setTime(180);
            currentSession.startBeforeGame(timerThread=new Thread(timer));
            timer.setTime(180);
            currentSession.startBeforeGame(timerThread=new Thread(timer));
            //check if another session has to be started
            for(String nickname : gameData.getPlayersList())
            {
                //create a list of winners
                if(gameData.getPoints(nickname)>=50)
                {
                    //add the winner
                    boolNextRound=false;
                }
            }
        }
        //TO-DO make transit the client to the winner state and send the winners
        //updateGame(gameData); to save the results of the all sessions
    }
      
    //public methods for the game creation
    public boolean AddPartecipant(String nicknamePlayer,ClientGameStub clientGameStub)
    {
        if (gameData.getPlayersList().size()+1>gameData.getNumPlayers())
        {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer,new ObserverClient(nicknamePlayer,clientGameStub,this,timer));
            startSession();
            return false;
        }
        else
        {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer,new ObserverClient(nicknamePlayer,clientGameStub,this,timer));
            return true;
        }
    }
    
    public boolean RemovePartecipant(String nicknamePlayer)
    {     
         if (gameData.getPlayersList().size()-1==0)
         {
            gameData.removePlayer(nicknamePlayer);
            observerClientSet.remove(nicknamePlayer);
            return false;
         }
        else
        {
            gameData.removePlayer(nicknamePlayer);
            observerClientSet.remove(nicknamePlayer);
            return true;
        }
    }
    
    public void exit ()
    {
        //TO-DO:advise the client that someone quit (by his nickname)
        observerClientSet.clear();//in case of an anomalous client system shutdown (also if the user click on X on the upper-right corner of the window)
        timerThread.interrupt();//interrupt the timer beacause of game ending
        persistentSignal.interruptGame();//interrupt the game itself
        boolNextRound=false;
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
        exit();   
    }
}