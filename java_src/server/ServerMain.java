package server;

import database.Database;
import database.DatabaseConfig;
import database.DatabaseImpl;

/**
 * Classe relativa all'avvio dell'applicazione lato server
 * @author Fedeli Andrea
 */
public class ServerMain {
    private static DbConnectScreen dbConnect;   //Interfaccia grafica connessione al db
    private static LoginScreen loginScreen; //Interfaccia grafica login amministratore
    private static RegisterScreen registerScreen;   //Interfaccia grafica registrazione dell'amministratore
    private static HomeScreen homeScreen;   //Interfaccia grafica amministratore
    
    /**
     * Inizializza le interfacce grafica dell'applicazione e il collegamento al database
     * @param args Gli argomenti passati all'applicazione come input dell'utente
     */
    public static void main(String[] args){
        System.setProperty("java.security.policy","file:./resources/policy.policy");
        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
        }
        dbConnect = new DbConnectScreen();
        
        dbConnect.setVisible(true);
    }
    
    /**
     * Permette la connessione del database e la visulizzazione della corretta interfaccia grafica
     * @param dbUser Utente del database
     * @param dbPassword Password del database
     * @param dbHost Host del database
     * @return <code>true</code> se la connessione al database è avvenuta
     */
    //Accedo al database e controllo la presenza di un amministratore
    public static boolean connectDatabase(String dbUser, String dbPassword, String dbHost){
        Database dbReference = DatabaseImpl.getDatabase().configure(new DatabaseConfig()
                 .setHost(dbHost).setUser(dbUser)
                 .setPswd(dbPassword));

        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        
        if(dbReference != null){
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
        }
        else{
            return false;
        }
    }
    
    /**
     * Permette la visualizzazione dell'interfaccia grafica per il login dell'amministratore
     */
    //Mostra la finestra di Login
    public static void showLogin(){
        loginScreen.setVisible(true);
    }
    
    /**
     * Permette la visualizzazione dell'interfaccia grafica relativa alla gestione del server da parte dell'amministratore
     */
    public static void showHome(){
        homeScreen.setVisible(true);
    }
}
