package client;

import entity.SessionData;
import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
/**
 * it'a an interface for the remote method of the game by the client side
 * @author Bianchi Edoardo
 */

public interface ClientGameStub extends Remote {

    static final int WAITING = 0;//timer di inizio gioco (visualizzo nella lobby, poi apro la finestra di gioco) -> appena viene raggiunto il num di giocatori
    static final int SESSION = 1;//transito in schrmata di gioco
    static final int RESULT = 2;//transito in schermata risultati sessione
    static final int WIN = 3;//transito schermata vincitori
    static final int ABANDONED = 4;//transito nello stato abbandonato se qualche altro giocatore abbandona al posto mio
    static final int ABANDONED_WINNER = 5;//transito nello stato abbandonato se qualche altro giocatore abbandona al posto mio

    /**
     * ritorna tutte le parole trovate dall'utente
     *
     * @return playerWords
     * @throws RemoteException
     */
    List<String> getWords() throws RemoteException;

    /**
     * logica per visualizzare risultati della sessione e invio di una lista di
     * nickname con relativo punteggio accumulato da n sessioni
     *
     * @param wordCheckedFound mappa di nome giocatore e lista di parole
     * @param pointPlayer mapp di nome giocatore e punteggio
     * @throws RemoteException
     */
    void updateSessionResults(Map<String, List<WordData>> wordCheckedFound, Map<String, Integer> pointPlayer) throws RemoteException;

    /**
     * logica per passare da uno stato di gioco all'altro
     *
     * @param gameState indica lo stato di gioco
     * @throws RemoteException
     */
    void changeGameState(int gameState) throws RemoteException;

    /**
     * logica per aggiornare i valori dei timer di gioco
     *
     * @param timerValue valore del timer
     * @throws RemoteException
     */
    void updateTimer(int timerValue) throws RemoteException;

    /**
     * logica per riempire la griglia grafica e numero della sessione
     *
     * @param grid griglia delle parole
     * @param numSession numero di sessione corrente
     * @throws RemoteException
     */
    void updateSessionGame(String[] grid, int numSession) throws RemoteException;

    /**
     * aggiorna la lista dei giocatori in lobby
     *
     * @param nickNames giocatori in lobby
     * @throws RemoteException
     */
    void updateLobby(List<String> nickNames) throws RemoteException;

    /**
     * logica per notificare vincitori o disertori
     *
     * @param nickNames giocatori
     * @throws RemoteException
     */
    void notifyInfoGame(List<String> nickNames) throws RemoteException;//questo metodo verrà usato sia per notificare i vincitori sia chi ha abbandonato

}
