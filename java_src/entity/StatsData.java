package entity;

import java.io.Serializable;
import java.util.List;
import utils.Pair;

public class StatsData implements Serializable{
    private Pair<String, Integer> bestPlayerGameScore;
    private Pair<String, Integer> bestPlayerSessionScore;
    private Pair<String, Integer> playerWithMoreSessions;
    private Pair<String, Integer> bestAverageSessionScore;
    private Pair<String, Integer> bestAverageGameScore;
    private Pair<String, Integer> playerWithMoreDuplicates;
    private Pair<String, Integer> playerWithMoreErrors;
    private List<Pair<String, Integer>> occurrencyWordsLeaderboard;
    private List<Pair<String, String>> wordsBestScore;
    private Pair<Integer, Integer>[] averageSessionsPerGame;
    private Pair<Integer, Integer>[] maxSessionsPerGame;
    private Pair<Integer, Integer>[] minSessionsPerGame;
    private List<Pair<String, Integer>> lettersAverageOccurency;
    private List<Pair<String, Integer>> occurrencyWordsDefLeaderboard;
    
    private static final long serialVersionUID = 1L;

    public Pair<String, Integer> getBestPlayerGameScore() {
        return bestPlayerGameScore;
    }

    public void setBestPlayerGameScore(Pair<String, Integer> bestPlayerGameScore) {
        this.bestPlayerGameScore = bestPlayerGameScore;
    }

    public Pair<String, Integer> getBestPlayerSessionScore() {
        return bestPlayerSessionScore;
    }

    public void setBestPlayerSessionScore(Pair<String, Integer> bestPlayerSessionScore) {
        this.bestPlayerSessionScore = bestPlayerSessionScore;
    }

    public Pair<String, Integer> getPlayerWithMoreSessions() {
        return playerWithMoreSessions;
    }

    public void setPlayerWithMoreSessions(Pair<String, Integer> playerWithMoreSessions) {
        this.playerWithMoreSessions = playerWithMoreSessions;
    }

    public Pair<String, Integer> getBestAverageSessionScore() {
        return bestAverageSessionScore;
    }

    public void setBestAverageSessionScore(Pair<String, Integer> bestAverageSessionScore) {
        this.bestAverageSessionScore = bestAverageSessionScore;
    }

    public Pair<String, Integer> getBestAverageGameScore() {
        return bestAverageGameScore;
    }

    public void setBestAverageGameScore(Pair<String, Integer> bestAverageGameScore) {
        this.bestAverageGameScore = bestAverageGameScore;
    }

    public Pair<String, Integer> getPlayerWithMoreDuplicates() {
        return playerWithMoreDuplicates;
    }

    public void setPlayerWithMoreDuplicates(Pair<String, Integer> playerWithMoreDuplicates) {
        this.playerWithMoreDuplicates = playerWithMoreDuplicates;
    }

    public Pair<String, Integer> getPlayerWithMoreErrors() {
        return playerWithMoreErrors;
    }

    public void setPlayerWithMoreErrors(Pair<String, Integer> playerWithMoreErrors) {
        this.playerWithMoreErrors = playerWithMoreErrors;
    }

    public List<Pair<String, Integer>> getOccurrencyWordsLeaderboard() {
        return occurrencyWordsLeaderboard;
    }

    public void setOccurrencyWordsLeaderboard(List<Pair<String, Integer>> occurrencyWordsLeaderboard) {
        this.occurrencyWordsLeaderboard = occurrencyWordsLeaderboard;
    }

    public List<Pair<String, String>> getWordsBestScore() {
        return wordsBestScore;
    }

    public void setWordsBestScore(List<Pair<String, String>> wordsBestScore) {
        this.wordsBestScore = wordsBestScore;
    }

    public Pair<Integer, Integer>[] getAverageSessionsPerGame() {
        return averageSessionsPerGame;
    }

    public void setAverageSessionsPerGame(Pair<Integer, Integer>[] averageSessionsPerGame) {
        this.averageSessionsPerGame = averageSessionsPerGame;
    }

    public Pair<Integer, Integer>[] getMaxSessionsPerGame() {
        return maxSessionsPerGame;
    }

    public void setMaxSessionsPerGame(Pair<Integer, Integer>[] maxSessionsPerGame) {
        this.maxSessionsPerGame = maxSessionsPerGame;
    }

    public Pair<Integer, Integer>[] getMinSessionsPerGame() {
        return minSessionsPerGame;
    }

    public void setMinSessionsPerGame(Pair<Integer, Integer>[] minSessionsPerGame) {
        this.minSessionsPerGame = minSessionsPerGame;
    }

    public List<Pair<String, Integer>> getLettersAverageOccurency() {
        return lettersAverageOccurency;
    }

    public void setLettersAverageOccurency(List<Pair<String, Integer>> lettersAverageOccurency) {
        this.lettersAverageOccurency = lettersAverageOccurency;
    }

    public List<Pair<String, Integer>> getOccurrencyWordsDefLeaderboard() {
        return occurrencyWordsDefLeaderboard;
    }

    public void setOccurrencyWordsDefLeaderboard(List<Pair<String, Integer>> occurrencyWordsDefLeaderboard) {
        this.occurrencyWordsDefLeaderboard = occurrencyWordsDefLeaderboard;
    }
    
    
}
