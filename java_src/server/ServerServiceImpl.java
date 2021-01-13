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

/**
 * La classe ServerServiceImpl rappresenta il server dell'applicazione e gestisce tutte le sue funzionalità, per questo implementa l'interfaccia.
 * remota ServerServiceStub
 * @author Fedeli Andrea
 * @see ServerServiceStub
 */
public class ServerServiceImpl extends Observable implements ServerServiceStub{
    private Map<String, ClientServiceStub> clientsList; //Client remoti - utilizzata per aggiornare i singoli client durante il login
    private Map<String, UserData> usersList;    //Dati dei client connessi
    private Map<Integer, Game> gamesList;   //Partite in corso
    private Database dbReference;   //riferimento al database
    private HomeScreen GUI; //riferimento all'interfaccia grafica
    private boolean statsChanged;   //flag per controllare se le statistiche sono cambiate
    private StatsData stats;    //statistiche di gioco

    /**
     * Costruttore della classe.
     * @param homeGUI L'interfaccia grafica del Server
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
    public ServerServiceImpl(HomeScreen homeGUI) throws RemoteException {
        clientsList = new HashMap<>();
        usersList = new HashMap<>();
        gamesList = new HashMap<>();
        dbReference = DatabaseImpl.getDatabase();
        GUI = homeGUI;
        statsChanged = false;
        
        stats = dbReference.getStats();
    }
    
    // <editor-fold defaultstate="collapsed" desc="interface methods">
    /**
     * Ripristina la password di un utente.
     * @param email L'email dell'utente di cui ripristinare la password
     * @return <code>true</code> se il reset è avvenuto
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see ServerServiceStub#recoverPassword(java.lang.String) 
     */
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

    /**
     * Aggiunge un ClientServiceStub alla mappa di Observer del Server.
     * @param nickname Il nickname dell'utente
     * @param client L'oggetto remoto del client
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see ServerServiceStub#addObserver(java.lang.String, client.ClientServiceStub) 
     */
    @Override
    public void addObserver(String nickname, ClientServiceStub client) throws RemoteException {
        clientsList.put(nickname, client);
        List<GameData> list = castMapToList();
        client.update(list);
        //client.update(stats);
        WrappedObserver wo = new WrappedObserver(this, client, nickname);
    }
    
    /**
     * Effettua il logout di un utente.
     * @param nickname Il nickname dell'utente
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see ServerServiceStub#logout(java.lang.String) 
     */
    @Override
    public void logout(String nickname) throws RemoteException {
        clientsList.remove(nickname);
        usersList.remove(nickname);
        
        GUI.stampEvent(nickname + " si è disconnesso");
    }

    /**
     * Aggiorna le informazioni relative ad un utente.
     * @param user L'utente da modificare
     * @param oldNickname Il vecchio nickname dell'utente
     * @return L'utente aggiornato
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see entity.UserData
     */
    @Override
    public UserData updateUserData(UserData user, String oldNickname) throws RemoteException {
        UserData updatedUser = dbReference.updateUser(user, oldNickname);
        /* Controllo vecchia password + aggiornamento nuova con invio email
        if(updateUser.getPassword().equals(user.getPassword)){
        new Thread(new EmailSender(user.getEmail(), "Il tuo account è stato modificato", user.getNickname(), 3)).start();
        */
        usersList.replace(oldNickname, updatedUser);
        
        GUI.stampEvent(oldNickname + "(" + user.getNickname() + ")" + " ha modificato l'account");
        return updatedUser;
    }

    /**
     * Registra un utente nell'applicazione.
     * @param newUser L'utente da registrare
     * @return <code>true</code> se la registrazione è avvenuta
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     */
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

    /**
     * Effettua il login all'applicazione.
     * @param email La mail dell'utente
     * @param password La password dell'utente
     * @return Restituisce un oggetto {@link utils.Pair}.
     * <p>
     * Il primo elemento è un {@link Integer} e rappresenta il codice di errore.
     * Il secondo è un {@link entity.UserData} e rappresenta l'utente corrispondente alle credenziali inserite.
     * <p>
     * {@link Integer} è <code>null</code> se il login è andato a buon fine. {@link entity.UserData} è <code>null</code> se il login è fallito.
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see utils.Pair
     */
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
    
    /**
     * Verifica il profilo di un utente
     * @param verificationCode Il codice di verifica dell'utente.
     * @param nickname Il nickname dell'utente
     * @return <code>true</code> se la verifica è avvenuta
     * @throws java.rmi.RemoteException Se si verificano problemi nella connessione RMI
     */
    @Override
    public boolean verifyUser(String verificationCode, String nickname) throws RemoteException{
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
    
    /**
     * Permette ad un utente di partecipare ad una partita.
     * @param nickname Nickname dell'utente.
     * @param gameId Identificatore numerico della partita.
     * @param client Oggetto remoto del client.
     * @return Oggetto remoto della partita.
     * @throws RemoteException Se si verificano problemi nella connessione RMI.
     * @see server.game.ServerGameStub
     * @see server.game.Game
     */
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
    
    /**
     * Permette ad un utente di creare una partita.
     * @param nickname Nickname dell'utente.
     * @param gameTitle Nome della partita.
     * @param numPlayers Numero dei giocatori della partita.
     * @param client Utente che ha creato della partita.
     * @return Oggetto remoto della partita.
     * @throws RemoteException Se si verificano problemi nella connessione RMI
     * @see server.game.ServerGameStub
     * @see server.game.Game
     */
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
    
    // <editor-fold defaultstate="collapsed" desc="public/protected methods">
    /**
     * Elimina una partita alla sua terminazione.
     * @param gameId L'identificativo della partita.
     * @see server.game.Game
     */
    public synchronized void disconnectGame(Integer gameId) {
        Game endedGame = gamesList.remove(gameId);
        
        if(endedGame.getGameData().getSessions().isEmpty()){
            //La partita si è interrotta nella lobby
            dbReference.removeGame(gameId);
        }
        else{
            stats = dbReference.getStats();
            statsChanged = true;  
        }
        this.setChanged();
        this.notifyObservers(statsChanged);
        statsChanged = false;
        
        GUI.stampEvent(endedGame.getGameData().getName() + " è terminata");
    }
    
    /**
     * Rimuove un oggetto client remoto dalla mappa di observer.
     * @param nickname Il nickname dell'utente corrispondente all'oggetto remoto.
     */
    public void removeObserver(String nickname){
        clientsList.remove(nickname);
    }
    
    /**
     * Chiude il server.
     */
    public void closeServer() {
        for (Map.Entry<Integer, Game> mapGameObj : gamesList.entrySet()) {
            mapGameObj.getValue().forcedExit(null);
        }

        for (Map.Entry<String, ClientServiceStub> mapClientObj : clientsList.entrySet()) {
            try{
                mapClientObj.getValue().shutDownServer();
            }
            catch(RemoteException e){
                clientsList.remove(mapClientObj.getKey());
            }
        }
        
        this.clientsList.clear();
        this.usersList.clear();
        this.gamesList.clear();
    }
    
    /**
     * Aggiorna la lista di partite dei client.
     */
    //Metodo per aggiornare numero di giocatori nella singola partita
    public synchronized void updateNumPlayer() {
         this.setChanged();
         this.notifyObservers(statsChanged);
    }
    
    /**
     * Restituisce le statistiche relative piattaforma.
     * @return Le statistiche della piattaforma.
     */
    protected StatsData getStats(){
        return this.stats;
    }
    
    /**
     * Restituisce la lista di partite
     * @return La lista di partite
     */
    protected List<GameData> getGamesList(){
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