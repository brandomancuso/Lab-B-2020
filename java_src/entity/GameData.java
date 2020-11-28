package entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameData implements Serializable{
    private Integer id;
    private String name;
    private List<SessionData> sessions;
    private int numPlayers;
    private Map<String, Integer> playerPoints;
    
    private static final long serialVersionUID = 1L;
    private final static int MAX_PLAYERS = 6;
    private final static int MIN_PLAYERS = 2;
    
    public GameData(){
        sessions = new LinkedList<>();
        playerPoints = new HashMap<>();
    }
    
    public GameData(int id) {
        this();
        this.id = id;
    }
    
    public GameData(String name, int numPlayers) {
        this();
        if(numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS){
            throw new IllegalArgumentException("numPlayers must be in range 2 - 6. Passed value: " + numPlayers);
        }
        this.name = name;
        this.numPlayers = numPlayers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
    
    public void addPlayer(String nickname) {
        playerPoints.put(nickname, null);
    }
    
    public void removePlayer(String nickname) {
        playerPoints.remove(nickname);
    }
    
    public void setPoints(String nickname, int points) {
        playerPoints.put(nickname, points);
    }
    
    public int getPoints(String nickname) {
        return playerPoints.get(nickname);
    }
    
    public List<String> getPlayersList() {
        return new LinkedList<>(playerPoints.keySet());
    }

    public void setSessions(List<SessionData> sessions) {
        this.sessions = sessions;
    }

    public List<SessionData> getSessions() {
        return sessions;
    }
    
    public void addSession(SessionData s){
        sessions.add(s);
    }
}
