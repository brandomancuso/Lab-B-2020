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
    public void updateSessionResults(Map<String, List<WordData>> wordCheckedFound, Map<String, Integer> pointPlayer) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeGameState(int gameState) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTimer(int timerValue) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSessionGame(String[] grid, int numSession) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLobby(List<String> nickName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyInfoGame(List<String> nickName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
