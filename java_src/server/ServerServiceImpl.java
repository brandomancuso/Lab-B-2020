package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import entity.GameData;
import entity.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import server.game.ServerGameStub;
import utils.Pair;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerServiceStub{
    private Map<String, ClientServiceStub> clientsList;
    private Map<String, User> usersList;
    //private Map<Integer, Game> gamesList;
    private Stack<Integer> freePort;
    private Map<Integer, Integer> occupiedPort;
    
    public ServerServiceImpl() throws RemoteException{
        clientsList = new HashMap<>();
        usersList = new HashMap<>();
        //gamesList = new HashMap<>();
        freePort = new Stack();
        occupiedPort = new HashMap<>();
    }
    
    @Override
    public Pair<String, User> login(String email, String password) throws RemoteException {
        return null;
    }

    @Override
    public boolean updateUserData(User user) throws RemoteException {
        return false;
    }

    @Override
    public String register(User newUser) throws RemoteException {
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
        clientsList.put(nickname, client);
    }

    @Override
    public void logout(String nickname) throws RemoteException {
        clientsList.remove(nickname);
        usersList.remove(nickname);
    }

    @Override //Deve restituire l'oggetto Game
    public ServerGameStub partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException {
        //Game game = gamesList.get(gameId);
        //if(game != null){
        //    game.addPartecipant(nickname, client);
        //    if(result.getSecond().booleanValue())
        //          return game;
        //    else
        //          return null;
        //}
        return null;
    }

    @Override //Deve restituire l'oggetto Game
    public ServerGameStub createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException {
        //User user = clientsLins.get(nickname);
        GameData gameData = new GameData(gameTitle, numPlayers);
        //game.setId(dbReference.addGame(dbGame));
        //Game game = new Game(gameData, nickname, client, this);
        //gamesList.put(gameData.getId(), gameData);
        //ServerGameStub gameStub = (ServerGameStub) UnicastRemoteObject.exportObject(game, freePort.peek());
        //occupiedPort.put(gameData.getId(),freePort.pop());
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
}
