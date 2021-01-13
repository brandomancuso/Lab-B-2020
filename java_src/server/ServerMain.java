package server;

import database.Database;
import database.DatabaseConfig;
import database.DatabaseException;
import database.DatabaseImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static DbConnectScreen dbConnect;
    private static LoginScreen loginScreen;
    private static RegisterScreen registerScreen;
    private static HomeScreen homeScreen;
    
    public static void main(String[] args){
        System.setProperty("java.security.policy","file:./resources/policy.policy");
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        dbConnect = new DbConnectScreen();
        
        dbConnect.setVisible(true);
    }
    
    //Accedo al database e controllo la presenza di un amministratore
    public static boolean connectDatabase(String dbUser, String dbPassword, String dbHost){
        Database dbReference = DatabaseImpl.getDatabase().configure(new DatabaseConfig()
                 .setHost(dbHost).setUser(dbUser)
                 .setPswd(dbPassword));

        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        
        if(dbReference != null){
            try {
                if(!dbReference.checkDatabaseExistence()){
                    dbReference.createDatabase();
                }
                homeScreen = new HomeScreen();
                if(dbReference.checkAdminExistence()){
                    loginScreen.setVisible(true);
                    return true;
                }
                else{
                    registerScreen.setVisible(true);
                    return true;
                }
            } catch (DatabaseException ex) {
                Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return false;
    }
    
    //Mostra la finestra di Login
    public static void showLogin(){
        loginScreen.setVisible(true);
    }
    
    public static void showHome(){
        homeScreen.setVisible(true);
    }
}
