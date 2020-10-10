package database;

import entity.GameData;
import entity.User;

public interface Database {
    User getUser();
    boolean addUser(User user);
    boolean updateUser();
    boolean addGame(GameData gameData);
    GameData getGame(int gameId);
}
