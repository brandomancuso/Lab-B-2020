package server;

import client.ClientServiceStub;
import entity.GameData;
import entity.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Pair;

/**!!!!!!!!!!!!!!!!!!IMPORTANTE!!!!!!!!!!!!!!!!!!!!!!!!!
 * I metodi utilizzano la classe GameData, ma io devo utilizzare la classe Game di Christian
 */

public class ServerServiceImpl extends UnicastRemoteObject implements ServerServiceStub{
    private List<ClientServiceStub> clientsList;
    private Map<String, User> onlineUsers;
    //private Map<Integer, Game> gamesList;
    //private Database dbReference;
    private HomeScreen homeScreen;
    private LoginScreen loginScreen;
    private RegisterScreen registerScreen;
    
    public ServerServiceImpl() throws RemoteException{
        clientsList = new ArrayList<>();
        onlineUsers = new HashMap<>();
        //gamesList = new HashMap<>();
        //dbReference = new Database();
        
        homeScreen = new HomeScreen();
        loginScreen = new LoginScreen();
        registerScreen = new RegisterScreen();
        loginScreen.setVisible(true);
    }
    
    @Override
    public Pair<String, User> login(String email, String password) throws RemoteException {
        //User dbUser = dbReference.getUser(email,password);
        //if(dbUser != null){
            //return new Pair<String, User>("OK", dbUser);
            //homeScreen.showInfo(dbUser.getNickname() + "has logged in!");
        //}
        //else{
            //return new Pair<String, User>("Credenziali errate!", null);
        //}
        return null;
    }

    @Override
    public boolean updateUserData(User user) throws RemoteException {
        //if(onlineUsers.put(user.getNickname(), user) != null)
        //    return true;
        //else
           return false;
    }

    @Override
    public String register(User newUser) throws RemoteException {
        //if(dbReference.addUser(newUser))
            //return "OK";
        //else
            //return "Errore!";
        return null;
    }

    @Override
    public boolean recoverPassword(String email) throws RemoteException {
        //mi serve il nickname dell'utente per avere il suo riferimento
        //invio la mail
        return false;
    }

    @Override
    public void addObserver(String nickname, ClientServiceStub client) throws RemoteException {
        clientsList.add(client);
    }

    @Override
    public void logout(String nickname) throws RemoteException {
        //mi serve anche il ClientStub per eliminarlo dalla lista di observers
        onlineUsers.remove(nickname);
        homeScreen.showInfo(nickname + "logout!");
    }

    @Override
    public boolean partecipate(String nickname, int gameId) throws RemoteException {
        //Game game = gamesList.get(gameId);
        //if(game != null){
        //    game.addPartecipant(nickname);
        //    homeScreen.showInfo(nickname + "has joined a game!");
        //    return true; //potrebbe essere meglio restituire direttamente l'oggetto partita
        //}
        //else
            return false;
    }

    @Override
    public boolean createGame(String nickname, String gameTitle, int numPlayers) throws RemoteException {
        User user = onlineUsers.get(nickname);
        GameData dbGame = new GameData(gameTitle, numPlayers);
        //dbGame.setId(dbReference.addGame(dbGame));
        //Game runtimeGame = new runtimeGame(dbGame, clientStub);
        //gamesList.put(runtimeGame.getId(), runtimeGame);
        homeScreen.showInfo(user.getNickname() + "has created a game!");
        return true; //potrebbe essere meglio restituire direttamente l'oggetto partita
    }
    
}
