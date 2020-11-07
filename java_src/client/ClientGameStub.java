package client;

import entity.Session;
import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientGameStub extends Remote{
    List<String> getWords() throws RemoteException;
    void updateWords(Map<String, List<WordData>> wordCheckedFound) throws RemoteException; 
    //void changeStateGame(StateGame stateGame) throws RemoteException;
    void update(int timerValue) throws RemoteException;
    void update(String[] grid) throws RemoteException;
}
