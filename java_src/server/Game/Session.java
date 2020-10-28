package server.Game;

import entity.*;
import java.util.*;

public class Session {
    SessionData sessionData;
    WordsMatrix wordsMatrix;
    Dictionary dictionary;
   
    public Session (WordsMatrix wordsMatrix,Dictionary dictionary)
    {
        this.sessionData=new SessionData();//every Session istantiated i've to create a SessionData Object
        this.wordsMatrix=new WordsMatrix();
        sessionData.setGrid(wordsMatrix.print());//set the grid of letter from WordsMatrix
        this.dictionary=dictionary;//the instance is created by the class Game

    }
    
    
    //method called by Game Class    
    public void checkWord(String nickname,String wordFound)
    {
       WordData wordTmp=new WordData();
       
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
                        wordTmp.setPoints(calculateScore(wordFound));  
                        wordTmp.setCorrect(true);
                    }
       
        sessionData.addWord(nickname, wordTmp);    
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