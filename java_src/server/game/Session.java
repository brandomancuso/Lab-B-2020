 package server.game;

import entity.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
 
 /**
  * all the game logic in inside here
  * @author Christian Squadrito
  */

public class Session {
    private SessionData sessionData;
    private WordsMatrix wordsMatrix;
    private Dictionary dictionary;
    private PersistentSignal persistentSignal;
    private Map<String,ObserverClient> observerClientSet;
    private GameData gameData;
    private int numSession;
    private Game game;
   
    public Session (Dictionary dictionary,PersistentSignal persistentSignal,Map<String,ObserverClient> observerClientSet,GameData gameData,int numSession,Game game)
    {
        this.sessionData=new SessionData();//every Session istantiated i've to create a SessionData Object
        this.wordsMatrix=new WordsMatrix();
        sessionData.setGrid(wordsMatrix.getWordsMatrix());//set the grid of letter from WordsMatrix
        this.dictionary=dictionary;//the instance is created by the class Game
        this.persistentSignal=persistentSignal;
        this.observerClientSet=(HashMap)observerClientSet;
        this.gameData=gameData;
        this.numSession=numSession;
        this.game=game;
    }

//public methods called by Game Class 
    
    /**
     * this method start the timer lobby and game server waits for it
     * @param timerThread the timer thread
     */
    
    public void startBeforeGame(Thread timerThread)
    {
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
        
    }
    
    /**
     * start the new game session and the timer session
     * @param timerThread the timer thread
     */
    
    public boolean startRealGame(Thread timerThread)
    {
        observerClientSet.forEach((key,value)->{
            try {
                    value.getClientGameStub().changeGameState(1);//change state into session
                    value.getClientGameStub().updateSessionGame(getWordMatrix(),numSession);                
                } catch (RemoteException ex) {
                    System.err.println(ex);
                    observerClientSet.remove(key);
                    game.removeObserverClient(key);
                    game.forcedExit(value.getNickname());//when a client isn't reacheable any more
                    System.exit(0);//the game has to be stopped because there isn't the all player
            }
        });
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            return true;//when the method forcedExit is call then the game is interupted
        }
        
        return false;
    }
    
    /**
     * check the wordfound and send the result to the game client player
     * @param timerThread the timer thread
     */
    public boolean startAfterGame(Thread timerThread)
    {
        //checkword
        observerClientSet.forEach((key,value)->
        {
            try {
                //request the words
                value.setWordsFound((ArrayList<String>)value.getClientGameStub().getWords());
                //check the words requested
                checkWord(value.getNickname(), value.getWordsFound());
            } catch (RemoteException ex) {
                System.err.println(ex);
                observerClientSet.remove(key);
                game.removeObserverClient(key);
                game.setBoolNextRound(false);//even if a client is disconnected, i permit to see the result of the game
            }
        });
        
        //set points of the players
        observerClientSet.forEach((key,value)->
        {
            int pointPlayer=countPoint(key);
            gameData.setPoints(value.getNickname(),gameData.getPoints(value.getNickname())+pointPlayer);//instead i could have used just the realPoints to know the playerPoint
        });
        
        
        //send result to client
        observerClientSet.forEach((key,value)->
        {
            try {
                value.getClientGameStub().changeGameState(2);//change in watching Result State
                value.getClientGameStub().updateSessionResults(sessionData.getFoundWords(),gameData.getPlayerPoints());               
             } catch (RemoteException ex) {
                System.err.println(ex);
                observerClientSet.remove(key);
                game.removeObserverClient(key);
                game.setBoolNextRound(false);//even if a client is disconnected, i permit to see the result of the game
            }
        });
        
        
        timerThread.start();
        try {
            persistentSignal.waitTimer();
        } catch (InterruptedException ex) {
            return true;//when the method forcedExit is call then the game is interupted
        }
        
       return false;
    }
    
    /**
     * get the grid of letter
     * @return String[] the grid of letter
     */
    public String[] getWordMatrix()
    {
        return wordsMatrix.getWordsMatrix();
    }
    
    /**
     * get all the information about the current session
     * @return SessionData containing all the session information 
     */
    public SessionData getSessionData()
    {
        return sessionData;
    }
    
  
//utility methods    
    private void checkWord(String nickname,List<String> wordFoundList)
    {
       WordData wordTmp;
       
       wordFoundList =removeDuplicated(wordFoundList);
       
       //TO-DO:if the wordFoundList is null set a empty result
       for (String wordFound : wordFoundList)
       {
            wordTmp = new WordData();
            wordFound=wordFound.trim();//to avoid space
            wordFound=wordFound.toLowerCase();//to avoid problem with the dictionary
            wordTmp.setInGrid(wordsMatrix.isAllowed(wordFound.toUpperCase()));
            wordTmp.setDuplicate(false);
            wordTmp.setInDictionary(dictionary.exists(wordFound));
            
             if (calculateScore(wordFound)!= 0)//at least with 3 letter
             {
                wordTmp.setMinimunLetter(true);
                if(!wordsMatrix.isAllowed(wordFound.toUpperCase()))//To be comparable with UpperCase inside the matrix
                 {
                     wordTmp.setPoints(calculateScore(wordFound));
                     wordTmp.setRealPoints(0);
                     wordTmp.setCorrect(false);
                 }
                  else
                    if (!dictionary.exists(wordFound))
                        {
                             wordTmp.setPoints(calculateScore(wordFound));
                             wordTmp.setRealPoints(0);
                             wordTmp.setCorrect(false);
                        }
                     else
                         if(isDuplicated(wordFound))
                             {
                                 wordTmp.setPoints(calculateScore(wordFound));
                                 wordTmp.setRealPoints(0);
                                 wordTmp.setCorrect(true);
                                 wordTmp.setDuplicate(true);
                             }
                          else
                             {
                                 wordTmp.setRealPoints(calculateScore(wordFound));
                                 wordTmp.setPoints(calculateScore(wordFound));  
                                 wordTmp.setCorrect(true);
                             }
             }
             else
                 wordTmp.setMinimunLetter(false);
             wordTmp.setDuplicate(isDuplicated(wordFound));
             wordTmp.setWord(wordFound);
             sessionData.addFoundWord(nickname, wordTmp);   
       }
    }   
    
    private boolean isDuplicated (String wordFound)
    {
        //TO-DO:if a word is duplicated by the same person
        List<WordData> wordFoundList=sessionData.getFoundWords().values().parallelStream().flatMap(Collection::stream).collect(Collectors.toList());//i will take all the word (This allows us to flatten the nested Stream structure and eventually collect all elements to a particular collection) 
        List<WordData> wordFoundMatched=wordFoundList.parallelStream().filter(word -> (word.getWord().equals(wordFound))).collect(Collectors.toList());//i filter in order to find the equal element
        if(wordFoundMatched.isEmpty())
            return false;
        else
        {
            wordFoundMatched.parallelStream().forEach(word ->
            {
                word.setRealPoints(0);
                word.setDuplicate(true);
            });
            return true;
        }
    }
    
    private int calculateScore (String word)
    {
         //calculate the score
        switch(word.length())
        {
            case 1:
            case 2:
                return 0;
                
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
    
    private int countPoint (String nickname)
    {
        List<WordData> foundWordPlayer=sessionData.getFoundWords().get(nickname);
        int  pointPlayer=0;
        if (foundWordPlayer != null)
        {
            for(WordData word : foundWordPlayer)
            {
                pointPlayer+=word.getRealPoints();
            }
         }
        return pointPlayer;
    }
    
    private List<String> removeDuplicated (List<String> foundWordList)
    {
        return foundWordList.parallelStream().distinct().collect(Collectors.toList());
    }
}
