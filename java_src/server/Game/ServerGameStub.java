package server.Game;

import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerGameStub extends Remote{
    Term requestWordDef (WordData word) throws RemoteException;
    void ready(String nickname) throws RemoteException;
    void leaveGame(String nickname) throws RemoteException;
}
