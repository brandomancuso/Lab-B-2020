package server;

import database.Database;
import database.DatabaseImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    private static ServerServiceImpl server;
    private static DbConnectScreen dbConnect;
    private static LoginScreen loginScreen;
    private static RegisterScreen registerScreen;
    private static HomeScreen homeScreen;
    
    public void main(String[] args) throws RemoteException{
        server = new ServerServiceImpl();
        dbConnect = new DbConnectScreen();
        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        homeScreen = new HomeScreen();
        
        dbConnect.setVisible(true);
    }
    
    //Accedo al database e controllo la presenza di un amministratore
    public static boolean connectDatabase(String dbUser, String dbPassord, String dbHost){
        Database dbReference = DatabaseImpl.getDatabase();
        
        if(dbReference.checkAdminExistence()){
            loginScreen.setVisible(true);
            return true;
        }
        else{
            registerScreen.setVisible(true);
            return false;
        }
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
