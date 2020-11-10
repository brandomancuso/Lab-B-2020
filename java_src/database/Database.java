package database;

import entity.GameData;
import entity.StatsData;
import entity.User;

public interface Database {
    User getUser(String nickname);
    User getUser(String email, String password);
    User addUser(User user);
    User updateUser(User user);
    User removeUser(String nickname);
    GameData addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(GameData gameData);
    void removeGame(int gameId);
    StatsData getStats();
    Database configure(DatabaseConfig config);
}
