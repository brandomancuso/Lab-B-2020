package database;

import entity.GameData;
import entity.StatsData;
import entity.UserData;
import utils.Pair;

public interface Database {
    UserData getUser(String nickname);
    UserData getUserByEmail(String email);
    Pair<UserData, Integer> getUser(String email, String password);
    UserData addUser(UserData user);
    UserData updateUser(UserData user, String old_nickname);
    UserData removeUser(String nickname);
    GameData addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(GameData gameData);
    void removeGame(int gameId);
    StatsData getStats();
    
    Database configure(DatabaseConfig config);
    boolean checkAdminExistence() throws DatabaseException;
    boolean checkDatabaseExistence() throws DatabaseException;
    void createDatabase();
    void deleteDatabase();
}
