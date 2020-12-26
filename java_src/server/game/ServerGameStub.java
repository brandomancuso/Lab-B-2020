 package server.game;

import entity.Term;
import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerGameStub extends Remote{
    Term requestWordDef (String nickname,String word) throws RemoteException;
    void ready(String nickname) throws RemoteException;
    void leaveGame(String nickname) throws RemoteException;
}
