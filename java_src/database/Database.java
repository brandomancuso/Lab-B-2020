package database;

import entity.GameData;
import entity.StatsData;
import entity.User;

public interface Database {
    User getUser(String nickname);
    User getUser(String email, String password);
    User addUser(User user);
    boolean updateUser(User user);
    GameData addGame(GameData gameData);
    GameData getGame(int gameId);
    boolean updateGame(GameData gameData);
    StatsData getStats();
}
