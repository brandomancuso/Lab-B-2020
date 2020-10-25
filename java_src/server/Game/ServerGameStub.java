package server;

import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerGameStub extends Remote{

    void sendWords(String nickname, List<WordData> words) throws RemoteException;
    String requestWordDef(WordData word) throws RemoteException;
    void ready(String nickname) throws RemoteException;
    void exit() throws RemoteException;  
}
