package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * it'a a class DAO for the session
 * @author Mancuso Brando
 */
public class SessionData implements Serializable{

    private int id;
    private Map<String, List<WordData>> foundWords;
    private Map<String, List<WordData>> requestedWords;
    private String[] grid;
    
    private static final long serialVersionUID = 1L;

    public SessionData(String[] grid) {
        this();
        this.grid = grid;        
    }

    public SessionData() {
        foundWords = new HashMap();
        requestedWords = new HashMap();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, List<WordData>> getFoundWords() {
        return foundWords;
    }

    public void setFoundWords(Map<String, List<WordData>> foundWords) {
        this.foundWords = foundWords;
    }

    public String[] getGrid() {
        return grid;
    }

    public void setGrid(String[] grid) {
        this.grid = grid;
    }
    
    public void addFoundWord(String nickname, WordData word){
        List<WordData> list = foundWords.get(nickname);
        if(list == null){
            list = new LinkedList<>();
            foundWords.put(nickname, list);
        }
        list.add(word);
    }
    
    public void addRequestedWord(String nickname, WordData word){
        List<WordData> list = requestedWords.get(nickname);
        if(list == null){
            list = new LinkedList<>();
            requestedWords.put(nickname, list);
        }
        //i check before if the same nickname has already requested the word
        if(!requestedWords.get(nickname).contains(word))
            list.add(word);
    }

    public Map<String, List<WordData>> getRequestedWords() {
        return requestedWords;
    }
    
}
