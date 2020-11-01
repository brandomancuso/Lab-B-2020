package client;

import entity.Session;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientGameStub extends Remote{
    List<String> sendWords() throws RemoteException;
    void changeStateGame(StateGame stateGame) throws RemoteException
    void update(int timerValue) throws RemoteException;
    void update(String[] grid) throws RemoteException;
}
