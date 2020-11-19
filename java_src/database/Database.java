package database;

import entity.GameData;
import entity.StatsData;
import entity.User;
import utils.Pair;

public interface Database {
    User getUser(String nickname);
    Pair<User, Integer> getUser(String email, String password);
    User addUser(User user);
    User updateUser(User user, String old_nickname);
    User removeUser(String nickname);
    GameData addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(GameData gameData);
    void removeGame(int gameId);
    StatsData getStats();
    
    Database configure(DatabaseConfig config);
    boolean checkAdminExistence();
    boolean checkDatabaseExistence();
    void createDatabase();
    void deleteDatabase();
}
