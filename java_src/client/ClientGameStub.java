package client;

import entity.Session;
import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientGameStub extends Remote{
    static final int WAITING = 0;//timer di inizio gioco (visualizzo nella lobby, poi apro la finestra di gioco) -> appena viene raggiunto il num di giocatori
    static final int SESSION = 1;//transito in schrmata di gioco
    static final int RESULT = 2;//transito in schermata risultati sessione
    static final int WIN = 3;//transito schermata vincitori
    
    
    List<String> getWords() throws RemoteException;//tutte le parole trovate dall'utente --> lista 
    void updateSessionResults(Map<String, List<WordData>> wordCheckedFound) throws RemoteException;//aggiornamento finestra fine sessione --> logica x visualizzare risultati della sessione
    void changeGameState(int gameState) throws RemoteException;
    void updateTimer(int timerValue) throws RemoteException;//valore sec x sec dei timer (tutti)
    void updateGrid(String[] grid) throws RemoteException;//mi passi le lettere da mettere nela griglia grafica
    void updateLobby(List<String> nickName);//mi manda la lista dei giocatori in sala di attesa
}
