package game.server;

import entity.Word;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerGameStub extends Remote{
    Term requestWordDef(Word word) throws RemoteException;
    void ready(String nickname) throws RemoteException; 
    void leaveGame(String nickname) throws RemoteException;
}
