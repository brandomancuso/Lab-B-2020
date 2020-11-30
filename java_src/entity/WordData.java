package entity;

import java.io.Serializable;

public class WordData implements Serializable{
    private String word;
    private int points;
    private int realPoints;
    private boolean correct;
    private boolean duplicate;
    private boolean defRequested;
    // private String definition; ?
    
    private static final long serialVersionUID = 1L;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isDefRequested() {
        return defRequested;
    }

    public void setDefRequested(boolean defRequested) {
        this.defRequested = defRequested;
    }
    
    
}
