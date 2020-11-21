package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import entity.User;
import entity.UserData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import server.game.ServerGameStub;
import utils.Pair;

public interface ServerServiceStub extends Remote{
    Pair<String, UserData> login(String email, String password) throws RemoteException;
    User updateUserData(UserData user, String oldUsername) throws RemoteException;
    String register(UserData newUser) throws RemoteException;
    boolean recoverPassword(String email) throws RemoteException;
    void addObserver(String nickname, ClientServiceStub client) throws RemoteException;
    void logout(String nickname) throws RemoteException;
    ServerGameStub partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException;
    ServerGameStub createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException;
}
