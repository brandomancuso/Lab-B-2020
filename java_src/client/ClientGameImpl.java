package client;

import entity.WordData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edoardo
 */
public class ClientGameImpl extends UnicastRemoteObject implements ClientGameStub {
   
    public ClientGameImpl() throws RemoteException {
        
    }

    @Override
    public List<String> getWords() throws RemoteException {
        return null;

    }

    @Override
    public void updateSessionResults(Map<String, List<WordData>> wordCheckedFound) throws RemoteException {

    }

    @Override
    public void changeGameState(int gameState) throws RemoteException {

    }

    @Override
    public void update(int timerValue) throws RemoteException {

    }

    @Override
    public void update(String[] grid) throws RemoteException {

    }

}
