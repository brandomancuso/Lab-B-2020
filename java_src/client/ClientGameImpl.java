package client;

import entity.WordData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edoardo
 */
public class ClientGameImpl extends UnicastRemoteObject implements ClientGameStub {

    private List<String> lobby;
    private int timerValue;
    private int gameState;
    private Lobby guiLobby;

    public ClientGameImpl(Lobby parGuiLobby) throws RemoteException {
        lobby = new ArrayList<String>();
        guiLobby = parGuiLobby;
    }

    //GETTER
    public synchronized List<String> getLobbyList() {
        return lobby;
    }

    public synchronized int getTimerValue() {
        return timerValue;
    }

    public synchronized int getGameState() {
        return gameState;
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
        this.gameState = gameState;
        switch (gameState) {
            case 0: //waiting
                break;
            case 1: //session
                break;
            case 2: //result
                break;
            case 3: //win
                break;
            case 4: //abandoned
                break;
        }
    }

    @Override
    public void updateTimer(int timerValue) throws RemoteException {
        this.timerValue = timerValue;
        this.guiLobby.updateTimer(timerValue);
    }

    @Override
    public void updateSessionGame(String[] grid, int numSession) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateLobby(List<String> nickName) throws RemoteException {
        lobby = nickName;
        this.guiLobby.fillPartecipant();
    }

    @Override
    public void notifyInfoGame(List<String> nickName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
