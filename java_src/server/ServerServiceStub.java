package server;

import client.ClientServiceStub;
import entity.UserData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import utils.Pair;

public interface ServerServiceStub extends Remote{
    Pair<String, UserData> login(String email, String password) throws RemoteException;
    boolean updateUserData(UserData user) throws RemoteException;
    String register(UserData newUser) throws RemoteException;
    boolean recoverPassword(String email) throws RemoteException;
    void addObserver(String nickname, ClientServiceStub client) throws RemoteException;
    void logout(String nickname) throws RemoteException;
    boolean partecipate(String nickname, int gameId) throws RemoteException;
    boolean createGame(String nickname, String gameTitle, int numPlayers) throws RemoteException;
}
