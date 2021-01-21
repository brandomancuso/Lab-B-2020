package client;

import entity.WordData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;

/**
 * Implementazione di ClientGameStub
 *
 * @author Edoardo
 */
public class ClientGameImpl extends UnicastRemoteObject implements ClientGameStub {

    private List<String> lobbyList;
    private int timerValue;
    private int gameState;
    static private Lobby guiLobby;
    static private GameWin guiGame;
    private ControlFrame guiMain;
    static private ResultWin guiResult;
    private Map<String, Integer> storePointPlayer;
    private Map<String, List<WordData>> wordCheckedFound;

    public ClientGameImpl() throws RemoteException {
        super(0);
        storePointPlayer = new HashMap<>();
        lobbyList = new ArrayList<String>();
        guiGame = null;
        guiResult = null;
    }

    /**
     * setta l'oggetto relativo alla lobby grafica
     *
     * @param parGuiLobby
     */
    public void setGuiLobby(Lobby parGuiLobby) {
        guiLobby = parGuiLobby;
    }

    /**
     * setta l'oggetto relativo alla finestra di gioco grafica
     *
     * @param parGuiGame
     */
    public void setGuiGame(GameWin parGuiGame) {
        guiGame = parGuiGame;
    }

    /**
     * setta l'oggetto relativo alla finestra principale grafica
     *
     * @param parGuiMain
     */
    public void setGuiMain(ControlFrame parGuiMain) {
        guiMain = parGuiMain;
    }

    /**
     * setta l'oggetto relativo alla finestra dei risultati grafica
     *
     * @param parGuiResult
     */
    public void setGuiResult(ResultWin parGuiResult) {
        guiResult = parGuiResult;
    }

    /**
     * ritorna la lista di giocatori in lobby
     *
     * @return
     */
    public synchronized List<String> getLobbyList() {
        return lobbyList;
    }

    /**
     * ritorna il valore del timer
     *
     * @return
     */
    public synchronized int getTimerValue() {
        return timerValue;
    }

    /**
     * ritorna lo stato di gioco
     *
     * @return
     */
    public synchronized int getGameState() {
        return gameState;
    }

    /**
     * ritorna una mappa contenente nomi e punti dei giocatori
     *
     * @return
     */
    public synchronized Map<String, Integer> getStorePointPlayer() {
        return storePointPlayer;
    }

    /**
     * ritorna una mappa contenente nome giocatore e una lista di parole trovate
     *
     * @return
     */
    public synchronized Map<String, List<WordData>> getWordCheckedFound() {
        return wordCheckedFound;
    }

    /**
     * ritorna tutte le parole trovate dall'utente
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public List<String> getWords() throws RemoteException {
        return guiGame.getPlayerWords();

    }

    /**
     * logica per visualizzare risultati della sessione e invio di una lista di
     * nickname con relativo punteggio accumulato da n sessioni
     *
     * @param wordCheckedFound mappa di nome giocatore e lista di parole
     * @param pointPlayer mapp di nome giocatore e punteggio
     * @throws RemoteException
     */
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

    /**
     * logica per passare da uno stato di gioco all'altro
     *
     * @param gameState indica lo stato di gioco
     * @throws RemoteException
     */
    @Override
    public void changeGameState(int gameState) throws RemoteException {
        this.gameState = gameState;
        switch (gameState) {
            case 0: //waiting --> blocco il bottone leave e parte il timer
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        guiLobby.disableLeaveBtn();
                    }
                });

                break;
            case 1: //session --> apro finestra di gioco
                if (this.guiResult != null) {
                    this.guiResult.setVisible(false);
                }
                this.guiLobby.setVisible(false);
                this.guiLobby.openGameWindow();
                this.guiGame.fillScoreTable();
                break;
            case 2: //result
                this.guiGame.disableInput();
                this.guiGame.setVisible(false);
                this.guiGame.openResultWindow();

                //chiudo game e apro result win
                break;
            case 3: //win --> transuto a lista di partita --> unico caso in cui distruggo guiGame??
                this.guiGame.dispose();
                this.guiResult.dispose();
                this.guiLobby.dispose();
                break;
            case 4: //abandoned --> transuto a lista di partita--> unico caso in cui distruggo guiGame??
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ClientGameImpl.guiGame.dispose();
                        ClientGameImpl.guiLobby.dispose();
                    }
                });
                break;
            case 5:
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ClientGameImpl.guiGame.dispose();
                        ClientGameImpl.guiResult.dispose();
                        ClientGameImpl.guiLobby.dispose();
                    }
                });
                break;
        }
    }

    /**
     * logica per aggiornare i valori dei timer di gioco
     *
     * @param timerValue valore del timer
     * @throws RemoteException
     */
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

    /**
     * logica per riempire la griglia grafica e numero della sessione
     *
     * @param grid griglia delle parole
     * @param numSession numero di sessione corrente
     * @throws RemoteException
     */
    @Override
    public void updateSessionGame(String[] grid, int numSession) throws RemoteException {
        this.guiGame.setSessionNum(numSession);
        this.guiGame.fillGameGrid(grid);
    }

    /**
     * aggiorna la lista dei giocatori in lobby
     *
     * @param nickNames giocatori in lobby
     * @throws RemoteException
     */
    @Override
    public void updateLobby(List<String> nickNames) throws RemoteException {
        lobbyList = nickNames;
        this.guiLobby.fillPartecipant();
    }

    /**
     * logica per notificare vincitori o disertori
     *
     * @param nickNames giocatori
     * @throws RemoteException
     */
    @Override
    public void notifyInfoGame(List<String> nickNames) throws RemoteException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String nickName = "";
                int length = nickNames.size();
                String playerAbandoned="";

                if (nickNames.get(0) != null)
                {
                   if (gameState == 5)
                    {
                        playerAbandoned=nickNames.get(0);//at the first place there will be the player who abandoned
                        nickNames.remove(0);
                    }
                   
                    while (!nickNames.isEmpty()) {
                        nickName += nickNames.get(nickNames.size() - 1) + " ";
                        nickNames.remove(nickNames.size() - 1);
                    }

                    if (gameState == 3) {
                        if (length >= 2) {
                            showMessageDialog(guiMain, nickName + " hanno vinto !");
                        } else {
                            showMessageDialog(guiMain, nickName + " ha vinto !");
                        }
                    }
                    if (gameState == 4) {
                        showMessageDialog(guiMain, nickName + " ha abbandonato !");
                    }

                    if (gameState == 5) {
                        if (length >= 3) {
                            showMessageDialog(guiMain, playerAbandoned + " ha abbandonato" + " e " + nickName + "hanno vinto !");
                        }
                        else
                        {
                            showMessageDialog(guiMain, playerAbandoned + " ha abbandonato" + " e " + nickName + "ha vinto !");
                        }
                    }
                }
            }
        });
    }
}
