 package server.game;

import client.ClientGameStub;
import entity.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Session {
    private SessionData sessionData;
    private WordsMatrix wordsMatrix;
    private Dictionary dictionary;
    private PersistentSignal persistentSignal;
    private Map<String,ObserverClient> observerClientSet;
    private GameData gameData;
   
    public Session (Dictionary dictionary,PersistentSignal persistentSignal,Map<String,ObserverClient> observerClientSet,GameData gameData)
    {
        this.sessionData=new SessionData();//every Session istantiated i've to create a SessionData Object
        this.wordsMatrix=new WordsMatrix();
        sessionData.setGrid(wordsMatrix.getWordsMatrix());//set the grid of letter from WordsMatrix
        this.dictionary=dictionary;//the instance is created by the class Game
        this.persistentSignal=persistentSignal;
        this.observerClientSet=(HashMap)observerClientSet;
        this.gameData=gameData;
    }
 
    //method called by Game Class 
    public void startBeforeGame(Thread timerThread)
    {
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
        
    }
    
    public void startRealGame(Thread timerThread)
    {
        observerClientSet.forEach((key,value)->{
            try {
                    value.getClientGameStub().updateGrid(getWordMatrix());
                    value.getClientGameStub().changeGameState(1);//change state into session
                } catch (RemoteException ex) {
                    System.err.println(ex);
            }
        });
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
    
    public void startAfterGame(Thread timerThread)
    {
        observerClientSet.forEach((key,value)->
        {
            try {
                //request the words
                value.setWordsFound((ArrayList<String>)value.getClientGameStub().getWords());
                //check the words requested
                int pointPlayer=checkWord(value.getNickname(), value.getWordsFound());
                //Set the point for the player
                gameData.setPoints(value.getNickname(),gameData.getPoints(value.getNickname())+pointPlayer);
                //send the word with points
                value.getClientGameStub().updateSessionResults(sessionData.getFoundWords());
                value.getClientGameStub().changeGameState(2);//change in watching Result State
            } catch (RemoteException ex) {
                System.err.println(ex);
            }
        });
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
      
    private int checkWord(String nickname,List<String> wordFoundList)
    {
       int pointPlayer=0;
       WordData wordTmp=new WordData();
       for (String wordFound : wordFoundList)
       {
            if(!wordsMatrix.isAllowed(wordFound))
             {
                 wordTmp.setPoints(calculateScore(wordFound));
                 wordTmp.setCorrect(false);
             }
              else
                if (!dictionary.exists(wordFound))
                    {
                         wordTmp.setPoints(calculateScore(wordFound));
                         wordTmp.setCorrect(false);
                    }
                 else
                     if(isDuplicated(wordFound))
                         {
                             wordTmp.setPoints(calculateScore(wordFound));
                             wordTmp.setCorrect(true);
                             wordTmp.setDuplicate(true);
                         }
                      else
                         {
                             pointPlayer=calculateScore(wordFound);//to know how many point the player has
                             wordTmp.setPoints(calculateScore(wordFound));  
                             wordTmp.setCorrect(true);
                         }

             sessionData.addWord(nickname, wordTmp);   
       }
       return pointPlayer;
    }   
    
    public String[] getWordMatrix()
    {
        return wordsMatrix.getWordsMatrix();
    }
    
    public SessionData getSessionData()
    {
        return sessionData;
    }
   
    //utility method   
    private boolean isDuplicated (String wordFound)
    {
        Map<String, List<WordData>> wordFoundMap= new HashMap<>(sessionData.getFoundWords());//i take from the return the hashmap of words found
        List<WordData> wordFoundList=new LinkedList<>((List)wordFoundMap.values());//i take all the list of word of every player
        
        return wordFoundList.stream().anyMatch(word -> (word.getWord().equals(wordFound)));//it's a functional operation of a foreach comparing wordFound with the all word in the session
    }
    
    private int calculateScore (String word)
    {
         //calculate the score
        switch(word.length())
        {
            case 3:
            case 4:    
                return 1;
               
            case 5:
                return 2;
                
            case 6:
                return 3;
                
            case 7:
                return 5;
              
            //it represents the case 8 or more because a word accepted which has less than 3 letter isn't allowed, so the last 
            //possible path is the default case
            default:
                 return 11;           
        }
    }
}
