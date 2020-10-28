package server;

import entity.Word;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerGameStub extends Remote{

    void sendWords(String nickname, List<Word> words) throws RemoteException;
    String requestWordDef(Word word) throws RemoteException;
    void ready(String nickname) throws RemoteException; 
}
