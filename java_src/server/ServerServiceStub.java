package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import server.game.ServerGameStub;
import utils.Pair;

public interface ServerServiceStub extends Remote{
    Pair<String, User> login(String email, String password) throws RemoteException;
    boolean updateUserData(User user) throws RemoteException;
    String register(User newUser) throws RemoteException;
    boolean recoverPassword(String email) throws RemoteException;
    void addObserver(String nickname, ClientServiceStub client) throws RemoteException;
    void logout(String nickname) throws RemoteException;
    Game partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException;
    Game createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException;
}
