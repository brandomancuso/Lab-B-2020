package entity;
import java.io.Serializable;

public class WordData implements Serializable {

    private Integer id;
    private String word;
    private int points;
    private int realPoints;
    private boolean correct;
    private boolean duplicate;
    private boolean inDictionary;
    private boolean inGrid;
    private boolean minimunLetter;

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

    public boolean inDictionary() {
        return inDictionary;
    }

    public void setInDictionary(boolean inDictionary) {
        this.inDictionary = inDictionary;
    }

    public boolean inGrid() {
        return inGrid;
    }

    public void setInGrid(boolean inGrid) {
        this.inGrid = inGrid;
    }
    
    public boolean minimunLetter() {
        return minimunLetter;
    }

    public void setMinimunLetter(boolean minimunLetter) {
        this.minimunLetter=minimunLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRealPoints() {
        return realPoints;
    }

    public void setRealPoints(int realPoint) {
        this.realPoints = realPoint;
    }
    
    @Override
    public boolean equals (Object word)
    {
        return this.word.equals(((WordData)word).getWord());
    }

}
