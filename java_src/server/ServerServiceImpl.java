package server;

import client.ClientGameStub;
import client.ClientServiceStub;
import database.Database;
import database.DatabaseImpl;
import entity.GameData;
import entity.StatsData;
import entity.UserData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import server.game.Game;
import server.game.ServerGameStub;
import utils.Pair;

public class ServerServiceImpl extends Observable implements ServerServiceStub{
    private Map<String, ClientServiceStub> clientsList; //Per aggiornare client singoli
    private Map<String, UserData> usersList;
    private Map<Integer, Game> gamesList;
    private Database dbReference;
    private HomeScreen GUI;
    private boolean statsChanged;
    private StatsData stats;

    public ServerServiceImpl(HomeScreen homeGUI) throws RemoteException {
        clientsList = new HashMap<>();
        usersList = new HashMap<>();
        gamesList = new HashMap<>();
        dbReference = DatabaseImpl.getDatabase();
        GUI = homeGUI;
        statsChanged = false;
        
        //stats = dbReference.getStats();
    }
    
    // <editor-fold defaultstate="collapsed" desc="interface methods">
    @Override
    public boolean recoverPassword(String email) throws RemoteException {
        //mi serve il nickname dell'utente per avere il riferimento per aggiornare il database
        //invio la mail
        String tempPsw = generatePassword();
        new Thread(new EmailSender(email, tempPsw, email, 2)).start();
        //TODO modifica password dell'utente nel database
        
        GUI.stampEvent(email + " ha richisto il recupero psw");
        return true;
    }

    @Override
    public void addObserver(String nickname, ClientServiceStub client) throws RemoteException {
        clientsList.put(nickname, client);
        List<GameData> list = castMapToList();
        client.update(list);
        //client.update(stats);
        WrappedObserver wo = new WrappedObserver(this, client, nickname);
    }

    @Override
    public void logout(String nickname) throws RemoteException {
        clientsList.remove(nickname);
        usersList.remove(nickname);
        
        GUI.stampEvent(nickname + " si è disconnesso");
    }

    @Override
    public UserData updateUserData(UserData user, String oldNickname) throws RemoteException {
        UserData updatedUser = dbReference.updateUser(user, oldNickname);
        usersList.replace(oldNickname, updatedUser);
        
        GUI.stampEvent(oldNickname + "(" + user.getNickname() + ")" + " ha modificato l'account");
        return updatedUser;
    }

    @Override
    public boolean register(UserData newUser) throws RemoteException {
        boolean registerResult;
        newUser.setActive(false);
        newUser.setActivationCode(generateCode());
        UserData updatedNewUser = dbReference.addUser(newUser);

        if (updatedNewUser != null) {
            registerResult = true;
            new Thread(new EmailSender(newUser.getEmail(), newUser.getActivationCode(), newUser.getNickname(), 1)).start();
            GUI.stampEvent(updatedNewUser.getNickname() + " registrato");
        } else {
            registerResult = false;
        }
        
        return registerResult;
    }

    @Override
    public Pair<Integer, UserData> login(String email, String password) throws RemoteException {
        Pair<Integer, UserData> loginResult;
        Pair<UserData, Integer> dbResult = dbReference.getUser(email, password);
        if (dbResult.getFirst() != null) {
            if(!dbResult.getFirst().isAdmin()){
                if(dbResult.getFirst().getActive()){
                    loginResult = new Pair<>(null, dbResult.getFirst());
                    usersList.put(dbResult.getFirst().getNickname(), dbResult.getFirst());
                    GUI.stampEvent(dbResult.getFirst().getNickname() + " ha effettuato il login");
                }
                else{
                    //Utente non verificato
                    loginResult = new Pair<>(2, null);
                }
            }
            else{
                //Utente amministratore
                loginResult = new Pair<>(3, null);
            }
        } else {
            //Credenziali errate
            int controlCode = dbResult.getLast();
            loginResult = new Pair<>(controlCode, null);
        }
        return loginResult;
    }
    
    @Override
    public boolean verifyUser(String verificationCode, String nickname){
        UserData dbResult = dbReference.getUser(nickname);
        boolean result = false;
        
        if(dbResult != null){
            if(dbResult.getActivationCode().equals(verificationCode)){
                result = true;
                dbResult.setActive(true);
                dbReference.updateUser(dbResult, dbResult.getNickname());
                GUI.stampEvent(nickname + " si è verificato");
            }
            else{
                result = false;
            }   
        }
        else{
            result = false;
        }
        
        return result;
    }
    
    @Override
    public ServerGameStub partecipate(String nickname, int gameId, ClientGameStub client) throws RemoteException {
        Pair<GameData, Boolean> result;
        Game game = gamesList.get(gameId);
        if (game != null) {
            result = game.AddPartecipant(nickname, client);
            if (result.getLast()) {
                this.setChanged();
                this.notifyObservers(castMapToList());
                GUI.stampEvent(nickname + " si è unito alla partita " + game.getGameData().getName());
                return game;
            } else {
                return null;
            }
        }
        return null;
    }
    
    @Override
    public ServerGameStub createGame(String nickname, String gameTitle, int numPlayers, ClientGameStub client) throws RemoteException {
        Boolean flag = true;
        ServerGameStub gameStub = null;
        ClientServiceStub user = clientsList.get(nickname);
        GameData gameData = new GameData(gameTitle, numPlayers,nickname);
        gameData = dbReference.addGame(gameData);
        Game game = new Game(gameData, nickname, client, this, dbReference);
        gamesList.put(gameData.getId(), game);

        while (flag) {
            try {
                //it try to connect on random port(Port 0 is used for listeners to let the OS pick up a non-assigned port and it always works (unless there is really no port available which is nearly impossible))
                flag = false;
                gameStub = (ServerGameStub) UnicastRemoteObject.exportObject(game, 0);
            } catch (RemoteException e) {
                flag = true;//if the connection tempt somehow went wrong
            }
        }
        this.setChanged();
        this.notifyObservers(statsChanged);
        GUI.stampEvent("Partita " + gameTitle + " creata da " + nickname);
        return gameStub;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="public methods">
    public synchronized void disconnectGame(Integer gameId) {
        Game endedGame = gamesList.remove(gameId);
        
        if(endedGame.getGameData().getSessions().isEmpty()){
            //La partita si è interrotta nella lobby
            dbReference.removeGame(gameId);
        }
        else{
          statsChanged = true;  
        }
        this.setChanged();
        this.notifyObservers(statsChanged);
        
        GUI.stampEvent(endedGame.getGameData().getName() + " è terminata");
    }

    public void removeObserver(String nickname){
        clientsList.remove(nickname);
    }
    
    public void closeServer(){
        this.clientsList.clear();
        this.usersList.clear();
        
        //TODO Annullare partite in corso
        this.gamesList.clear();
        
        /**
         *  for (Map.Entry<Integer, Game> game : gamesList.entrySet()) {
         *      game.serverClosing();
         *  }
         *  
         *  for (Map.Entry<String, ClientServiceStub> client : clientsList.entrySet()) {
         *      client.serverClosing();
         *  }
         * 
         *  this.clientsList.clear();
         *  this.usersList.clear();
         *  this.gamesList.clear();
         */
    }
    
    //Metodo per aggiornare numero di giocatori nella singola partita
    public synchronized void updateNumPlayer() {
         this.setChanged();
         this.notifyObservers(statsChanged);
    }
    
    public StatsData getStats(){
        return this.stats;
    }
    
    public List<GameData> getGamesList(){
        return castMapToList();
    }
    // </editor-fold>
    
    //  <editor-fold defaultstate="collapsed" desc="private methods">
    private String generateCode() {
        String code = new String();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            Integer codeChar = rand.nextInt(10);
            code = code + codeChar.toString();
        }
        return code;
    }

    private String generatePassword() {
        final String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        final int N = alphabet.length();
        
        Random r = new Random();
        String newPswd = "";
        
        for(int i=0; i<8; i++){
            newPswd = newPswd + alphabet.charAt(r.nextInt(N));
        }
        return newPswd;
    }
    
    private List<GameData> castMapToList(){
       ArrayList<Game> tempList  = new ArrayList<Game>( gamesList.values() );
       ArrayList<GameData> returnedList = new ArrayList();
       for(Game g : tempList){
           returnedList.add(g.getGameData());
       }
       
       return returnedList;
    }
    // </editor-fold>
}