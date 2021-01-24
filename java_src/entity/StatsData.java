package entity;

import java.io.Serializable;
import java.util.List;
import utils.Pair;

/**
 * it'a a class DAO for the statistics
 * @author Mancuso Brando
 */
public class StatsData implements Serializable{
    private Pair<String, Integer> bestPlayerGameScore;
    private Pair<String, Integer> bestPlayerSessionScore;
    private Pair<String, Integer> playerWithMoreSessions;
    private Pair<String, Double> bestAverageSessionScore;
    private Pair<String, Double> bestAverageGameScore;
    private Pair<String, Integer> playerWithMoreDuplicates;
    private Pair<String, Integer> playerWithMoreErrors;
    private List<Pair<String, Integer>> occurrencyWordsLeaderboard;
    private List<Pair<String, String>> wordsBestScore;
    private Pair<Integer, Double>[] averageSessionsPerGame;
    private Pair<Integer, Integer>[] maxSessionsPerGame;
    private Pair<Integer, Integer>[] minSessionsPerGame;
    private List<Pair<String, Double>> lettersAverageOccurency;
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

    public Pair<String, Double> getBestAverageSessionScore() {
        return bestAverageSessionScore;
    }

    public void setBestAverageSessionScore(Pair<String, Double> bestAverageSessionScore) {
        this.bestAverageSessionScore = bestAverageSessionScore;
    }

    public Pair<String, Double> getBestAverageGameScore() {
        return bestAverageGameScore;
    }

    public void setBestAverageGameScore(Pair<String, Double> bestAverageGameScore) {
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

    public Pair<Integer, Double>[] getAverageSessionsPerGame() {
        return averageSessionsPerGame;
    }

    public void setAverageSessionsPerGame(Pair<Integer, Double>[] averageSessionsPerGame) {
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

    public List<Pair<String, Double>> getLettersAverageOccurency() {
        return lettersAverageOccurency;
    }

    public void setLettersAverageOccurency(List<Pair<String, Double>> lettersAverageOccurency) {
        this.lettersAverageOccurency = lettersAverageOccurency;
    }

    public List<Pair<String, Integer>> getOccurrencyWordsDefLeaderboard() {
        return occurrencyWordsDefLeaderboard;
    }

    public void setOccurrencyWordsDefLeaderboard(List<Pair<String, Integer>> occurrencyWordsDefLeaderboard) {
        this.occurrencyWordsDefLeaderboard = occurrencyWordsDefLeaderboard;
    }
    
    
}
