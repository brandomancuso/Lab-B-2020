 package server.game;

import entity.Term;
import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * interface for remote method of the game
 * @author Christian Squadrito
 */

public interface ServerGameStub extends Remote{
    Term requestWordDef (String nickname,String word) throws RemoteException;
    void ready() throws RemoteException;
    void leaveGame(String nickname) throws RemoteException;
}
