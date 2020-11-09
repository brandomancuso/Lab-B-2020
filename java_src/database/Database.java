package database;

import entity.GameData;
import entity.StatsData;
import entity.UserData;

public interface Database {
    UserData getUser(String nickname);
    UserData getUser(String email, String password);
    boolean addUser(UserData user);
    boolean updateUser(UserData user);
    int addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(GameData gameData);
    StatsData getStats();
}
