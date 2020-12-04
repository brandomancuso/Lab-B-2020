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

    private List<String> lobbyList;
    private List<String> playerWordList;
    private int timerValue;
    private int gameState;
    private Lobby guiLobby;
    private GameWin guiGame;

    public ClientGameImpl() throws RemoteException {
        lobbyList = new ArrayList<String>();
        playerWordList = new ArrayList<String>();
    }

    //SETTER
    public void setGuiLobby(Lobby parGuiLobby) {
        guiLobby = parGuiLobby;
    }

    public void setGuiGame(GameWin parGuiGame) {
        guiGame = parGuiGame;
    }

    //GETTER
    public synchronized List<String> getLobbyList() {
        return lobbyList;
    }

    public synchronized int getTimerValue() {
        return timerValue;
    }

    public synchronized int getGameState() {
        return gameState;
    }

    @Override
    public List<String> getWords() throws RemoteException {
        return playerWordList;

    }

    @Override
    public void updateSessionResults(Map<String, List<WordData>> wordCheckedFound, Map<String, Integer> pointPlayer) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeGameState(int gameState) throws RemoteException {
        this.gameState = gameState;
        switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                this.guiLobby.disableLeaveBtn();
                break;
            case 1: //session --> apro finestra di gioco
                this.guiLobby.openGameWindow();
                this.guiLobby.setVisible(false);
                this.guiLobby.dispose();
                break;
            case 2: //result
                this.guiGame.disableInput();
                playerWordList = this.guiGame.getPlayerWords();
                //chiudo game e apro result win
                break;
            case 3: //win --> transuto a lista di partita
                break;
            case 4: //abandoned --> transuto a lista di partita
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
        lobbyList = nickName;
        this.guiLobby.fillPartecipant();
    }

    @Override
    public void notifyInfoGame(List<String> nickName) throws RemoteException {
        //metodo per notificare chi abbandona e chi vince
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
