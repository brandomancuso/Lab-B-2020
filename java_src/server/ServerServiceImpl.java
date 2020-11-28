package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import database.Database;
import database.DatabaseImpl;
import entity.GameData;
import entity.UserData;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import server.game.Game;
import server.game.ServerGameStub;
import utils.Pair;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServerServiceImpl implements ServerServiceStub{
    private Map<String, ClientServiceStub> clientsList;
    private Map<String, UserData> usersList;
    private Map<Integer, Game> gamesList;
    private Stack<Integer> freePort;
    private Map<Integer, Integer> occupiedPort;
    private Database dbReference;
    
    public ServerServiceImpl() throws RemoteException{
        clientsList = new HashMap<>();
        usersList = new HashMap<>();
        gamesList = new HashMap<>();
        freePort = new Stack();
        occupiedPort = new HashMap<>();
        dbReference = DatabaseImpl.getDatabase();
    }

    @Override
    public boolean recoverPassword(String email) throws RemoteException {
        //mi serve il nickname dell'utente per avere il riferimento per aggiornare il database
        //invio la mail
        try{
            sendEmail("", "", email, "Verifica account Il Paroliere", "");
            return false;
        }
        catch(MessagingException e){
            HomeScreen.stampEvent("Invio email fallito!");
            return false;
        }
    }

    @Override
    public void addObserver(String nickname, ClientServiceStub client) throws RemoteException {
        clientsList.put(nickname, client);
    }

    @Override
    public void logout(String nickname) throws RemoteException {
        clientsList.remove(nickname);
        usersList.remove(nickname);
    }

    @Override
    public UserData updateUserData(UserData user, String oldNickname) throws RemoteException {
        UserData updatedUser = dbReference.updateUser(user, oldNickname);
        usersList.replace(oldNickname, updatedUser);
        return updatedUser;
    }
    
    //TODO Modificare da String a boolean
    @Override
    public String register(UserData newUser) throws RemoteException {
        try{
            String registerResult;
            UserData updatedNewUser = dbReference.addUser(newUser);

            if(updatedNewUser != null){
                registerResult = "Registrazione completata!";
                //TODO Invio mail all'utente tramite thread per diminuire il ritardo
                sendEmail("", "", updatedNewUser.getEmail(), "Verifica account Il Paroliere", "");
                HomeScreen.stampEvent(updatedNewUser.getNickname() + " registrato!");
                return registerResult;
            }
            else{
                registerResult = "Errore durante la registrazione!";
                HomeScreen.stampEvent(updatedNewUser.getNickname() + ": errore durante la registrazione!");
                return registerResult;
            }
        }
        catch(MessagingException e){
            e.printStackTrace();
            HomeScreen.stampEvent("Invio email fallito!");
            return null;
        }
    }
    
    @Override
    public Pair<String, UserData> login(String email, String password) throws RemoteException {
        Pair<String, UserData> loginResult;
        Pair<UserData, Integer> dbResult = dbReference.getUser(email, password);
        UserData userResult = dbResult.getFirst();
        int infoResult = dbResult.getLast();
        
        if(userResult != null){
            loginResult = new Pair<>(null, userResult);
            usersList.put(userResult.getNickname(), userResult);
            return loginResult;
        }
        else{
            if(infoResult == 1){
                loginResult = new Pair<>("Email errata!", null);
                return loginResult;
            }
            else{
                loginResult = new Pair<>("Password errata!", null);
                return loginResult;
            }
        }
    }
    
    @Override
    public ServerGameStub partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException {
        Pair<GameData, Boolean> result;
        Game game = gamesList.get(gameId);
        if(game != null){
            result = game.AddPartecipant(nickname, client);
            if(result.getLast())
                  return game;
            else
                  return null;
        }
        return null;
    }

    @Override
    public ServerGameStub createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException {
        ClientServiceStub user = clientsList.get(nickname);
        GameData gameData = new GameData(gameTitle, numPlayers);
        gameData = dbReference.addGame(gameData);
        Game game = new Game(gameData, nickname, client, this);
        gamesList.put(gameData.getId(), game);
        ServerGameStub gameStub = (ServerGameStub) UnicastRemoteObject.exportObject(game, freePort.peek());
        occupiedPort.put(gameData.getId(),freePort.pop());
        //TODO Notifica tutti gli utenti per l'aggiornamento delle partite
        return gameStub;
    }
    
    public void disconnectGame(Integer gameId){
        gamesList.remove(gameId);
        freePort.push(occupiedPort.remove(gameId));
        //TODO Notifica tutti gli utenti per l'aggiornamento delle partite
    }
    
    //Metodo per aggiornare numero di giocatori nella singola partita
    public void updateNumPlayer(Integer gameId){
        
    }
    
    private void sendEmail(String usr, String pwd, String to, String subject, String body) throws SendFailedException, MessagingException{
        String password=pwd;
	String username=usr;
	    	       
	String host = "smtp.office365.com";
	String from=username;
	   
        Properties props = System.getProperties();
	props.put("mail.smtp.host",host);
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.port",587);
	    
	Session session = Session.getInstance(props);
	    
	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
	msg.setSubject(subject);
	msg.setText(body);
	    
	Transport.send(msg,username,password);
    }
}
