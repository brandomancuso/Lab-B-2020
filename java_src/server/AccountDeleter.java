package server;

import database.Database;
import database.DatabaseImpl;
import entity.UserData;
import java.util.TimerTask;

/**
 * Timertask che si occupa di eliminare un account nel caso questo non venisse verificato.
 * @author Fedeli Andrea
 * @see java.util.TimerTask
 */
public class AccountDeleter extends TimerTask{
    private String email;    //email dell'utente da eliminare
    
    /**
     * Costruttore della classe
     * @param email Email dell'utente da eliminare
     */
    public AccountDeleter(String email){
        super();
        this.email = email;
    }
    
    /**
     * Operazioni per cancellare l'utente dal database
     * @see java.util.TimerTask#run() 
     */
    @Override
    public void run() {
        Database dbReference = DatabaseImpl.getDatabase();
        
        UserData result = dbReference.getUserByEmail(email);
        
        if(!result.getActive()){
            dbReference.removeUser(result.getNickname());
        }
    }
}
