package client;

import entity.WordData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Edoardo
 */
public class ClientGameImpl extends UnicastRemoteObject implements ClientGameStub {

    private List<String> lobbyList;
    //private List<String> playerWordList;
    private int timerValue;
    private int gameState;
    private Lobby guiLobby;
    private GameWin guiGame;
    private ControlFrame guiMain;
    private Map<String, Integer> storePointPlayer;

    public ClientGameImpl() throws RemoteException {
        super(0);
        storePointPlayer = new HashMap<>();
        lobbyList = new ArrayList<String>();
        //playerWordList = new ArrayList<String>();
        guiGame = null;
    }

    //SETTER
    public void setGuiLobby(Lobby parGuiLobby) {
        guiLobby = parGuiLobby;
    }

    public void setGuiGame(GameWin parGuiGame) {
        guiGame = parGuiGame;
    }

    public void setGuiMain(ControlFrame parGuiMain) {
        guiMain = parGuiMain;
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

    public synchronized Map<String, Integer> getStorePointPlayer() {
        return storePointPlayer;
    }

    @Override
    public List<String> getWords() throws RemoteException {
        return guiGame.getPlayerWords();

    }

    @Override
    public void updateSessionResults(Map<String, List<WordData>> wordCheckedFound, Map<String, Integer> pointPlayer) throws RemoteException {
        this.storePointPlayer = pointPlayer;
    }

    @Override
    public void changeGameState(int gameState) throws RemoteException {
        this.gameState = gameState;
        switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                this.guiLobby.disableLeaveBtn();
                break;
            case 1: //session --> apro finestra di gioco
                if (this.guiGame == null) {
                    //creo guiGame nuovo
                    this.guiLobby.openGameWindow();
                } else {
                    this.guiGame.setVisible(true);
                }
                //this.guiLobby.setVisible(false);
                //this.guiLobby.dispose();
                break;
            case 2: //result
                this.guiGame.disableInput();
                //playerWordList = this.guiGame.getPlayerWords();
                this.guiGame.setVisible(false);
                //chiudo game e apro result win
                break;
            case 3: //win --> transuto a lista di partita --> unico caso in cui distruggo guiGame??
                break;
            case 4: //abandoned --> transuto a lista di partita--> unico caso in cui distruggo guiGame??
                
                break;
        }
    }

    @Override
    public void updateTimer(int timerValue) throws RemoteException {     
            switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                 this.timerValue = timerValue;
                 this.guiLobby.updateTimer(this.timerValue);
                break;
            case 1: //session --> apro finestra di gioco
                this.timerValue = timerValue;
                this.guiGame.updateTimer(this.timerValue);
                break;
            case 2: //result
                
                break;
            case 3: //win --> transuto a lista di partita --> unico caso in cui distruggo guiGame??
                break;
            case 4: //abandoned --> transuto a lista di partita--> unico caso in cui distruggo guiGame??
                
                break;
            }
    }

    @Override
    public void updateSessionGame(String[] grid, int numSession) throws RemoteException {
        this.guiGame.setSessionNum(numSession);
        this.guiGame.fillGameGrid(grid);
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
