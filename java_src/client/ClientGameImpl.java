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
    private ResultWin guiResult;
    private Map<String, Integer> storePointPlayer;
    private Map<String, List<WordData>> wordCheckedFound;

    public ClientGameImpl() throws RemoteException {
        super(0);
        storePointPlayer = new HashMap<>();
        lobbyList = new ArrayList<String>();
        //playerWordList = new ArrayList<String>();
        guiGame = null;
        guiResult = null;

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

    public void setGuiResult(ResultWin parGuiResult) {
        guiResult = parGuiResult;
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

    public synchronized Map<String, List<WordData>> getWordCheckedFound() {
        return wordCheckedFound;
    }

    @Override
    public List<String> getWords() throws RemoteException {
        return guiGame.getPlayerWords();

    }

    @Override
    public void updateSessionResults(Map<String, List<WordData>> wordCheckedFound, Map<String, Integer> pointPlayer) throws RemoteException {
        this.storePointPlayer = pointPlayer;
        this.wordCheckedFound = wordCheckedFound;
        if (this.guiResult != null) {
            this.guiResult.fillResultTable();
            this.guiResult.fillScoreTable();
        }
        if (this.guiGame != null) {
            this.guiGame.fillScoreTable();
        }
    }

    @Override
    public void changeGameState(int gameState) throws RemoteException {
        this.gameState = gameState;
        switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                this.guiLobby.disableLeaveBtn();
                break;
            case 1: //session --> apro finestra di gioco
                if(this.guiResult != null){
                    this.guiResult.setVisible(false);
                }
                this.guiLobby.openGameWindow();
                /**if (this.guiGame == null) {
                    //creo guiGame nuovo
                    this.guiLobby.openGameWindow();
                } else {
                    this.guiGame.setVisible(true);
                }*/
                this.guiGame.fillScoreTable();
                //this.guiLobby.setVisible(false);
                //this.guiLobby.dispose();
                break;
            case 2: //result
                this.guiGame.disableInput();
                //playerWordList = this.guiGame.getPlayerWords();
                this.guiGame.setVisible(false);
                this.guiGame.openResultWindow();

                //chiudo game e apro result win
                break;
            case 3: //win --> transuto a lista di partita --> unico caso in cui distruggo guiGame??
                this.guiGame.dispose();
                break;
            case 4: //abandoned --> transuto a lista di partita--> unico caso in cui distruggo guiGame??
                this.guiGame.dispose();
                break;
        }
    }

    @Override
    public void updateTimer(int timerValue) throws RemoteException {
        this.timerValue = timerValue;
        switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                this.guiLobby.updateTimer(this.timerValue);
                break;
            case 1: //session --> apro finestra di gioco
                this.guiGame.updateTimer(this.timerValue);
                break;
            case 2: //result
                this.guiResult.updateTimer(this.timerValue);
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
    public void updateLobby(List<String> nickNames) throws RemoteException {
        lobbyList = nickNames;
        this.guiLobby.fillPartecipant();
    }

    @Override
    public void notifyInfoGame(List<String> nickNames) throws RemoteException {
        String nickName="";
        int length=nickNames.size();
        
        while(!nickNames.isEmpty())
        {
            nickName=nickNames.get(length-1)+ "  ";
            nickNames.remove(length-1);
        }
        

        if (gameState==3)
        {
            if (length >= 2)
                showMessageDialog(guiMain,nickName+ " hanno vinto !");
            else
                showMessageDialog(guiMain,nickName+ " ha vinto !");
        }     
        if (gameState==4)
             showMessageDialog(guiMain,nickName+ " ha abbandonato !");   
    }

}
