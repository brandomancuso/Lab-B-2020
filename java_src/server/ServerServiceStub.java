package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import entity.UserData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import server.game.ServerGameStub;
import utils.Pair;

/**
 * Interfaccia remota di ServerIP per la comunicazione tra client e server con RMI. 
 * @author Fedeli Andrea
 * @see ServerServiceImpl
 */
public interface ServerServiceStub extends Remote{
    
    /**
     * Effettua il login al server
     * @param email Email dell'utente.
     * @param password Password dell'utente.
     * @return Restituisce un oggetto {@link utils.Pair}
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    Pair<Integer, UserData> login(String email, String password) throws RemoteException;
    
    /**
     * Aggiorna le informazioni del profilo utente
     * @param user L'utente da modificare
     * @param oldUsername Il vecchio nickname dell'utente
     * @return L'oggetto {@link entity.UserData} aggiornato.
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    UserData updateUserData(UserData user, String oldUsername) throws RemoteException;
    
    /**
     * Registra un utente nella piattaforma.
     * @param newUser L'utente da registrare.
     * @return <code>true</code> se la registrazione è avvenuta
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    boolean register(UserData newUser) throws RemoteException;
    
    /**
     * Ripristina la password di un utente.
     * @param email L'email dell'utente
     * @return <code>true</code> se la verifica è avvenuta
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    boolean recoverPassword(String email) throws RemoteException;
    
    /**
     * Aggiunge un oggetto client remoto alla mappa di observer
     * @param nickname Il nickname dell'utente
     * @param client L'oggetto remoto del client
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    void addObserver(String nickname, ClientServiceStub client) throws RemoteException;
    
    /**
     * Effettua il logout di un utente
     * @param nickname Il nickname dell'utente
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    void logout(String nickname) throws RemoteException;
    
    /**
     * Permette a un utente di partecipare ad una partita
     * @param nickname Il nickname dell'utente
     * @param gameId L'identificatore della partita
     * @param client L'oggetto remoto del client
     * @return L'oggetto remoto della partita
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see server.game.ServerGameStub
     */
    ServerGameStub partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException;
    
    /**
     * Permette di creare una nuova partita
     * @param nickname Il nickname dell'utente
     * @param gameTitle Il nome della partita
     * @param numPlayers Il numero di giocatori
     * @param client L'oggetto remoto del client
     * @return L'oggetto remoto della partita
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see server.game.ServerGameStub
     */
    ServerGameStub createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException;
    
    /**
     * Permette di verificare il profilo di un utente
     * @param verificationCode Il codice di verifica dell'utente
     * @param nickname Il nickname dell'utente
     * @return <code>true</code> se la verifica è avvenuta
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    boolean verifyUser(String verificationCode, String nickname) throws RemoteException;
}
