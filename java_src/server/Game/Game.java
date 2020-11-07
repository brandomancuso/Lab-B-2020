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
    private Thread timerThread;
    private List<Session> sessionList;
    private Map<String,ObserverClient> observerClientSet;
    private Dictionary dictionary;
    private Boolean boolNextRound;
    private int playerReadyNextRound;
    private PersistentSignal persistentSignal;
    
    public Game (GameData gameData,String hostNickname,ClientGameStub clientGameStub)
    {
        this.gameData=gameData;
        observerClientSet=new HashMap<>();
        observerClientSet.put(hostNickname,new ObserverClient(hostNickname,clientGameStub,this));
        loadDictionary();
        sessionList=new ArrayList<>();
        gameData.addPlayer(hostNickname);
        boolNextRound=true;//i assume that there might be a little succesful at the end of the first round
        playerReadyNextRound=0;
        persistentSignal=new PersistentSignal();
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
    
    private void ExecuteGame()
    {
        //Start waiting for the beginning of the game by creating the timerThread
        StartBeforeGame();
        //Start the real Game
        StartRealGame();
        //Finish of the game
        StartAfterGame();
        //check if another session has to be started
        for(String nickname : gameData.getPlayersList())
            if(gameData.getPoints(nickname)>=50)
            {
                //TO-DO make transit the client to the winner state
                boolNextRound=false;
            }
        //if the bool is set to true means there aren't winner
        if(boolNextRound)
            ExecuteGame();//i start againg with another session
    }
    
    private void StartBeforeGame()
    {
        Timer timer=new Timer(30,persistentSignal);
        timerThread=new Thread(timer);
        observerClientSet.forEach((ObserverClient observerClient)->observerClient.setTimer(timer));
        //TO-DO:Remote Method for changing the state of the client
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
    private void StartRealGame()
    {
        Session currentSession=new Session(dictionary);
        Timer timer=new Timer(180,persistentSignal);
        timerThread=new Thread(timer);
        sessionList.add(currentSession);
        observerClientSet.forEach((ObserverClient observerClient)->{
            observerClient.setTimer(timer);
            try {
                    observerClient.getClientGameStub().update(currentSession.getWordMatrix());
                } catch (RemoteException ex) {
                    System.err.println();
            }
        });
        //TO-DO:Remote Method for changing the state of the client
        timerThread.start();
        try {
            persistentSignal.waitTimer();
            if (Thread.interrupted())
                    //unbind the object and use the method unicastRemoteObject.unexportedObject();
                    System.exit(0);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
    private void StartAfterGame()
    {
        int lastIndex=sessionList.size()-1;
        Timer timer=new Timer(180,persistentSignal);
        timerThread=new Thread(timer);
        //reequest of the list of the word found
        observerClientSet.forEach((ObserverClient observerClient)->
        {
            try {
                //request the words
                observerClient.setWordsFound((ArrayList<String>)observerClient.getClientGameStub().getWords());
                //check the words requested
                int pointPlayer=sessionList.get(lastIndex).checkWord(observerClient.getNickname(), observerClient.getWordsFound());
                //Set the point for the player
                gameData.setPoints(observerClient.getNickname(),gameData.getPoints(observerClient.getNickname())+pointPlayer);
                //send the word with points
                observerClient.getClientGameStub().sendWords(sessionList.get(lastIndex).getWordChecked());
                //set the timer
                observerClient.setTimer(timer);
            } catch (RemoteException ex) {
                System.err.println(ex);
            }
        });
        //TO-DO:Remote Method for changing the state of the client and check the word
        timerThread.start();
        try {
            persistentSignal.waitTimer();
            if (Thread.interrupted())
                //unbind the object and use the method unicastRemoteObject.unexportedObject();
                System.exit(0);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
     
    //public methods for server purpose
    public boolean AddPartecipant(String nicknamePlayer,ClientGameStub clientGameStub)
    {
        if (gameData.getPlayersList().size()+1>gameData.getNumPlayers())
        {
            ExecuteGame();
            return false;
        }
        else
        {
            gameData.addPlayer(nicknamePlayer);
            observerClientSet.put(nicknamePlayer,new ObserverClient(nicknamePlayer,clientGameStub,this));
            return true;
        }
    }
    
    public boolean RemovePartecipant(String nicknamePlayer)
    {     
         if (gameData.getPlayersList().size()-1==0)
            return false;
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
    }
    
    
    //remote methods for client purpose via RMI
    @Override
    public synchronized Definition requestWordDef(WordData word) throws RemoteException {
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