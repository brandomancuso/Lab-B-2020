package server;

import client.ClientServiceStub;
import entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import utils.Pair;

public interface ServerServiceStub extends Remote{
    //METODI OBSOLETI
    Pair<String, User> login(String email, String password) throws RemoteException;
    boolean updateUserData(User user) throws RemoteException;
    String register(User newUser) throws RemoteException;
    boolean recoverPassword(String email) throws RemoteException;
    void addObserver(String nickname, ClientServiceStub client) throws RemoteException;
    void logout(String nickname) throws RemoteException;
    boolean partecipate(String nickname, int gameId) throws RemoteException;
    boolean createGame(String nickname, String gameTitle, int numPlayers) throws RemoteException;
}
