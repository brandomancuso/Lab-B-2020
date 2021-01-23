package database;

import entity.GameData;
import entity.StatsData;
import entity.UserData;
import utils.Pair;

/**
 * Interfaccia per la comunicazione con il database
 * @author Mancuso Brando
 */
public interface Database {
    
    /**
     * Restituisce l'utente con il nickname dato
     * @param nickname Il nickname dell'utente da ricercare
     * @return L'oggetto <code>UserData</code> relativo all'utente trovato. Restituisce <code>null</code> se non è stato trovato alcun utente
     * @see entity.UserData
     */
    UserData getUser(String nickname);
    
    /**
     * Restituisce l'utente con la mail data
     * @param email La mail dell'utente
     * @return L'oggetto <code>UserData</code> relativo all'utente trovato. Restituisce <code>null</code> se non è stato trovato alcun utente
     * @see entity.UserData
     */
    UserData getUserByEmail(String email);
    
    /**
     * Restituisce l'utente con email e password date
     * @param email La mail dell'utente da ricercare
     * @param password La password dell'utente
     * @return Oggetto di tipo <code>Pair</code> con primo valore l'oggetto di tipo <code>UserData</code> e secondo valore un <code>int</code> rappresentante il codice di errore.
     * Se l'utente non è stato trovato <code>UserData</code> è <code>null</code>.
     * @see utils.Pair
     */
    Pair<UserData, Integer> getUser(String email, String password);
    
    /**
     * Aggiunge un utente al database
     * @param user L'utente da aggiungere
     * @return L'oggetto <code>UserData</code> relativo all'utente aggiunto. Se l'utente esiste già l'oggetto è <code>null</code>
     * @see entity.UserData
     */
    UserData addUser(UserData user);
    
    /**
     * Aggiorna le informazioni di un utente presente nel database
     * @param user L'utente con le informazioni aggiornate
     * @param old_nickname Il vecchio username dell'utente
     * @return L'oggetto <code>UserData</code> relativo all'utente aggiornato. Restituisce <code>null</code> se non è presente alcun utente con quel nickname
     * @see entity.UserData
     */
    UserData updateUser(UserData user, String old_nickname);
    
    /**
     * Rimuove un utente dal database
     * @param nickname Il nickname dell'utente da rimuovere
     * @return L'oggetto <code>UserData</code> relativo all'utente rimosso. Restituisce <code>null</code> se non è stato trovato alcun utente
     * @see entity.UserData
     */
    UserData removeUser(String nickname);
    
    /**
     * Aggiunge una partita al database
     * @param gameData L'oggetto relativo alla partita
     * @return L'oggetto <code>GameData</code> relativo alla partita aggiunta
     * @see entity.GameData
     */
    GameData addGame(GameData gameData);
    
    /**
     * Restituisce la partita con id dato
     * @param gameId L'identificativo della partita
     * @return L'oggetto <code>GameData</code> relativo alla partita ottenuta
     * @see entity.GameData
     */
    GameData getGame(int gameId);
    
    /**
     * Aggiorna le informazioni della partita relativa all'oggetto <code>GameData</code> dato
     * @param gameData La partita da aggiornare
     * @return L'oggetto <code>GameData</code> relativo alla partita modificata
     * @see entity.GameData
     */
    boolean updateGame(GameData gameData);
    
    /**
     * Rimuove la partita con id dato dal database
     * @param gameId L'identificativo della partita
     */
    void removeGame(int gameId);
    
    /**
     * Restituisce le statistiche calcolate sui dati del database
     * @return L'oggetto <code>StatsData</code> rappresentante tutte le statistiche del programma
     * @see entity.StatsData
     */
    StatsData getStats();
    
    /**
     * Configura il database
     * @param config L'oggetto <code>DatabaseConfig</code> per la configurazione
     * @return L'oggetto <code>Database</code>
     */
    Database configure(DatabaseConfig config);
    
    /**
     * Controlla se è presente un utente amministratore nel database
     * @return <code>true</code> se l'amministratore è presente
     * @throws DatabaseException Se le credenziali del database sono errate
     */
    boolean checkAdminExistence() throws DatabaseException;
    
    /**
     * Controlla se il database è già stato creato
     * @return <code>true</code> se il database esiste
     * @throws DatabaseException Se le credenziali del database sono errate
     */
    boolean checkDatabaseExistence() throws DatabaseException;
    
    /**
     * Crea il database
     */
    void createDatabase();
    
    /**
     * Cancella il database
     */
    void deleteDatabase();
}
