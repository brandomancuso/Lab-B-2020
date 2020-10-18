package database;

import entity.GameData;
import entity.StatsData;
import entity.User;

public interface Database {
    User getUser(String nickname);
    User getUser(String email, String password);
    boolean addUser(User user);
    boolean updateUser(User user);
    int addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(int gameId, GameData gameData);
    StatsData getStats();
}
