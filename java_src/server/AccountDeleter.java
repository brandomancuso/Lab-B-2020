package server;

import database.Database;
import database.DatabaseImpl;
import entity.UserData;
import java.util.TimerTask;


public class AccountDeleter extends TimerTask{
    private String nickname;
    
    public AccountDeleter( String nickname){
        super();
        this.nickname = nickname;
    }
    
    @Override
    public void run() {
        Database dbReference = DatabaseImpl.getDatabase();
        
        //Necessaria ricerca per email dell'utente
        UserData result = dbReference.getUser(nickname);
        
        if(!result.getActive()){
            //Necessaria calncellazione per email sul database
            dbReference.removeUser(nickname);
        }
    }
}
