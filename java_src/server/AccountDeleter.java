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
    private String nickname;
    
    /**
     * Costruttore della classe
     * @param nickname Nickname dell'utente da eliminare
     */
    public AccountDeleter( String nickname){
        super();
        this.nickname = nickname;
    }
    
    /**
     * Operazioni per cancellare l'utente dal database
     * @see @see java.util.TimerTask#run() 
     */
    @Override
    public void run() {
        Database dbReference = DatabaseImpl.getDatabase();
        
        UserData result = dbReference.getUser(nickname);
        
        if(!result.getActive()){
            dbReference.removeUser(nickname);
        }
    }
}
