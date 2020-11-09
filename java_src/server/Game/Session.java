package server.Game;

import client.ClientGameStub;
import entity.*;
import java.rmi.RemoteException;
import java.util.*;

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
        //TO-DO:Remote Method for changing the state of the client
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
    public void startRealGame(Thread timerThread)
    {
        observerClientSet.forEach((ObserverClient observerClient)->{
            try {
                    observerClient.getClientGameStub().update(getWordMatrix());
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
    
    public void startAfterGame(Thread timerThread)
    {
        //reequest of the list of the word found
        observerClientSet.forEach((ObserverClient observerClient)->
        {
            try {
                //request the words
                observerClient.setWordsFound((ArrayList<String>)observerClient.getClientGameStub().getWords());
                //check the words requested
                int pointPlayer=checkWord(observerClient.getNickname(), observerClient.getWordsFound());
                //Set the point for the player
                gameData.setPoints(observerClient.getNickname(),gameData.getPoints(observerClient.getNickname())+pointPlayer);
                //send the word with points
                observerClient.getClientGameStub().sendWords(getWordChecked());
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

    public List<WordData> getWordChecked ()
    {
       List<WordData> resultList=new ArrayList<>();
       for(List<WordData> wordPlayerList :sessionData.getFoundWords().values())
       {
           for(WordData wordPlayer : wordPlayerList)
                resultList.add(wordPlayer);
       }
       return resultList;
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
