package client;

import entity.SessionData;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientGameStub extends Remote{
    void update(int timerValue) throws RemoteException;
    void update(SessionData grid) throws RemoteException;
}
