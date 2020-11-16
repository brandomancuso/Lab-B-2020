package server;

import database.ConnectionManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    private static ServerServiceImpl server;
    private DbConnectScreen dbConnect;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
    private HomeScreen homeScreen;
    private ConnectionManager connectionManager;
    
    public void main(String[] args) throws RemoteException{
        server = new ServerServiceImpl();
        dbConnect = new DbConnectScreen();
        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        homeScreen = new HomeScreen();
        //connectionManager = ConnectionManager.getConnectionManager();
        
        dbConnect.setVisible(true);
    }
    
    //Accedo al database e controllo la presenza di un amministratore
    public static boolean connectDatabase(String dbUser, String dbPassord, String dbHost){
        //DatabaseConfig dbConfig = new DatabaseConfig();
        //dbConfig.setUser(dbUser);
        //dbConfig.setPassword(dbPassword);
        //dbConfig.setHost(dbHost);
        //connectionManager.configure(dbConfig);
        
        //if(connectionManager.checkDatabaseExistance()){
            //if(connectionManager.checkAdministratorExistance()){
                //loginScreen.setVisible(true);
                //dbConnect.setVisible(false);
            //}
            //else{
                //registerScreen.setVisible(true);
                //dbConnect.setVisible(false);
            //}
            //return true;
        //}
        return false;
    }
    
    //Pubblico il Server sul Registry
    public static void activateServer(){
        try{
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Il Paroliere", server);
            HomeScreen.stampEvent("Server is started...");
        }
        catch(Exception e){
            
        }
    }
}
