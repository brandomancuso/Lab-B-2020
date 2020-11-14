package client;

import entity.WordData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientGameStub extends Remote{
    static final int SESSION = 0;//transito in schrmata di gioco
    static final int RESULT = 1;//transito in schermata risultati sessione
    static final int WINNER = 2;//transito in schermata risultati sessione
    
    List<String> getWords() throws RemoteException;//tutte le parole trovate dall'utente --> lista 
    void updateSessionResults(Map<String, List<WordData>> wordCheckedFound) throws RemoteException;//aggiornamento finestra fine sessione --> logica x visualizzare risultati della sessione
    void changeGameState(int gameState) throws RemoteException;
    void sendWinner(Map<String, String> winner);
    void update(int timerValue) throws RemoteException;//valore sec x sec dei timer (tutti)
    void update(String[] grid) throws RemoteException;//mi passi le lettere da mettere nela griglia grafica
}
