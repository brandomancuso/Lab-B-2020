package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Session implements Serializable{
    private int id;
    private Map<String, List<Word>> foundWords;
    private String[] grid;
    
    private static final long serialVersionUID = 1L;

    public Session(String[] grid) {
        this();
        this.grid = grid;        
    }

    public Session() {
        foundWords = new HashMap();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, List<Word>> getFoundWords() {
        return foundWords;
    }

    public void setFoundWords(Map<String, List<Word>> foundWords) {
        this.foundWords = foundWords;
    }

    public String[] getGrid() {
        return grid;
    }

    public void setGrid(String[] grid) {
        this.grid = grid;
    }
    
    public void addWord(String nickname, Word word){
        List<Word> list = foundWords.get(nickname);
        if(list == null){
            list = new LinkedList<>();
            foundWords.put(nickname, list);
        }
        list.add(word);
    }
}
