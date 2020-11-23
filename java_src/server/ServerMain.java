package server;

import database.Database;
import database.DatabaseConfig;
import database.DatabaseImpl;

public class ServerMain {
    private static DbConnectScreen dbConnect;
    private static LoginScreen loginScreen;
    private static RegisterScreen registerScreen;
    private static HomeScreen homeScreen;
    
    public static void main(String[] args){
        dbConnect = new DbConnectScreen();
        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        homeScreen = new HomeScreen();

        dbConnect.setVisible(true);
    }
    
    //Accedo al database e controllo la presenza di un amministratore
    public static boolean connectDatabase(String dbUser, String dbPassword, String dbHost){
        Database dbReference = DatabaseImpl.getDatabase().configure(new DatabaseConfig()
                .setHost(dbHost).setUser(dbUser)
                .setPswd(dbPassword));
        
        if(dbReference != null){
            if(!dbReference.checkDatabaseExistence()){
                dbReference.createDatabase();
            }
            if(dbReference.checkAdminExistence()){
                loginScreen.setVisible(true);
                return true;
            }
            else{
                registerScreen.setVisible(true);
                return true;
            }
        }
        else{
            return false;
        }
    }
    
    //Mostra la finestra di Login
    public static void showLogin(){
        loginScreen.setVisible(true);
    }
    
    //Mostra la finestra di Home
    public static void showHome(){
        homeScreen.setVisible(true);
    }
}
