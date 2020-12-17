 package server.game;

import entity.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static javax.swing.JOptionPane.showMessageDialog;

public class Session {
    private SessionData sessionData;
    private WordsMatrix wordsMatrix;
    private Dictionary dictionary;
    private PersistentSignal persistentSignal;
    private Map<String,ObserverClient> observerClientSet;
    private GameData gameData;
    private int numSession;
   
    public Session (Dictionary dictionary,PersistentSignal persistentSignal,Map<String,ObserverClient> observerClientSet,GameData gameData,int numSession)
    {
        this.sessionData=new SessionData();//every Session istantiated i've to create a SessionData Object
        this.wordsMatrix=new WordsMatrix();
        sessionData.setGrid(wordsMatrix.getWordsMatrix());//set the grid of letter from WordsMatrix
        this.dictionary=dictionary;//the instance is created by the class Game
        this.persistentSignal=persistentSignal;
        this.observerClientSet=(HashMap)observerClientSet;
        this.gameData=gameData;
        this.numSession=numSession;
    }
 
//methods called by Game Class 
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
                    value.getClientGameStub().changeGameState(1);//change state into session
                    value.getClientGameStub().updateSessionGame(getWordMatrix(),numSession);                
                } catch (RemoteException ex) {
                    System.err.println(ex);
            }
        });
        
        //to wait all client changing its state
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timerThread.start();
        try {
            persistentSignal.waitTimer();
            if (Thread.interrupted())
                System.exit(0);//when the method exit is call then the game is interupted
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
    public void startAfterGame(Thread timerThread)
    {
        //checkword
        observerClientSet.forEach((key,value)->
        {
            try {
                //request the words
                value.setWordsFound((ArrayList<String>)value.getClientGameStub().getWords());
                //check the words requested
                int pointPlayer=checkWord(value.getNickname(), value.getWordsFound());
                //Set the point for the player
                gameData.setPoints(value.getNickname(),gameData.getPoints(value.getNickname())+pointPlayer); 
            } catch (RemoteException ex) {
                System.err.println(ex);
            }
        });
        
        //send result to client
        observerClientSet.forEach((key,value)->
        {
            try {
                value.getClientGameStub().changeGameState(2);//change in watching Result State
                value.getClientGameStub().updateSessionResults(sessionData.getFoundWords(),gameData.getPlayerPoints());               
             } catch (RemoteException ex) {
                System.err.println(ex);
            }
        });
        
        
        timerThread.start();
        try {
            persistentSignal.waitTimer();
            if (Thread.interrupted())
                System.exit(0);//when the method exit is call then the game is interupted
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
  
//utility methods    
    private int checkWord(String nickname,List<String> wordFoundList)
    {
       int pointPlayer=0;
       WordData wordTmp=new WordData();
       //TO-DO:if the wordFoundList is null set a empty result
       for (String wordFound : wordFoundList)
       {
            wordFound=wordFound.trim();//to avoid space
            wordFound=wordFound.toLowerCase();//to avoid problem with the dictionary
            if(!wordsMatrix.isAllowed(wordFound.toUpperCase()))//To be comparable with UpperCase inside the matrix
             {
                 wordTmp.setPoints(calculateScore(wordFound));
                 wordTmp.setCorrect(false);
                 pointPlayer+=0;
             }
              else
                if (!dictionary.exists(wordFound))
                    {
                         wordTmp.setPoints(calculateScore(wordFound));
                         wordTmp.setCorrect(false);
                         pointPlayer+=0;
                    }
                 else
                     if(isDuplicated(wordFound))
                         {
                             wordTmp.setPoints(calculateScore(wordFound));
                             wordTmp.setCorrect(true);
                             wordTmp.setDuplicate(true);
                             pointPlayer+=0;
                         }
                      else
                         {
                             wordTmp.setRealPoints(calculateScore(wordFound));
                             pointPlayer+=calculateScore(wordFound);//to know how many point the player has
                             wordTmp.setPoints(calculateScore(wordFound));  
                             wordTmp.setCorrect(true);
                         }
             wordTmp.setWord(wordFound);
             sessionData.addFoundWord(nickname, wordTmp);   
       }
       return pointPlayer;//I save the real pointPlayer not the false one for the DataBase
    }   
    
    private boolean isDuplicated (String wordFound)
    {
        List<WordData> wordFoundList=sessionData.getFoundWords().values().parallelStream().flatMap(Collection::stream).collect(Collectors.toList());//i will take all the word (This allows us to flatten the nested Stream structure and eventually collect all elements to a particular collection:)  
        return wordFoundList.parallelStream().anyMatch(word -> (word.getWord().equals(wordFound)));//it's a functional operation of a foreach comparing wordFound with the all word in the session
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
    
    public String[] getWordMatrix()
    {
        return wordsMatrix.getWordsMatrix();
    }
    
    public SessionData getSessionData()
    {
        return sessionData;
    }
}
