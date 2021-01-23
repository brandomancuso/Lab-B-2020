package database;

/**
 * DatabaseConfig modella le credenziali per configurare il database durante la sua creazione o il suo accesso
 * @author Mancuso Brando
 */
public class DatabaseConfig {
    String host;
    String user;
    String pswd;
    
    /**
     * Costruttore della classe
     */
    public DatabaseConfig(){
        
    }

    /**
     * Imposta il valore della variabile host
     * @param host L'host del database
     * @return L'oggetto di configurazione del database con host uguale al parametro assegnato
     */
    public DatabaseConfig setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Imposta il valore della variabile user
     * @param user Lo user del database
     * @return L'oggetto di configurazione del database con user uguale al parametro assegnato
     */
    public DatabaseConfig setUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * Imposta il valore della variabile password
     * @param pswd La password del database
     * @return L'oggetto di configurazione del database con password uguale al parametro assegnato
     */
    public DatabaseConfig setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    } 
}
