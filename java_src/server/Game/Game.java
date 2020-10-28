package server.Game;

import client.ClientGameStub;
import entity.GameData;
import entity.WordData;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Coustructor called after a master wants to create a Game
public class Game implements ServerGameStub{
    GameData gameData;
    Timer timer;
    Set<ObserverClient> observerClients;
    
    public Game (String nameGame,int numPlayer,String hostNickname,ClientGameStub clientGameStub)
    {
        gameData=new GameData(nameGame,numPlayer);
        observerClients=new HashSet<>();
        timer=new Timer();
        observerClients.add(new ObserverClient(timer,clientGameStub,this));
        gameData.addPlayer(hostNickname);
    }
    
    //private methods for game class purpose
    private void StartGame()
    {
        timer.setTime(30);//start after passing all the reference
    }
    
    //public methods for server purpose
    public boolean AddPartecipant(String nicknamePlayer,ClientGameStub clientGameStub)
    {
        if (gameData.getPlayersList().size()+1>gameData.getNumPlayers())
        {
            StartGame();
            return false;
        }
        else
        {
            gameData.addPlayer(nicknamePlayer);
            return true;
        }
    }
    
    public boolean RemovePartecipant(String nicknamePlayer)
    {
        
         if (gameData.getPlayersList().size()-1==0)
            return false;
        else
        {
            gameData.removePlayer(nicknamePlayer);
            return true;
        }
    }
    
    public void exit (ObserverClient observerClintRemoved)
    {
        observerClients.remove(observerClintRemoved);//in case of an anomalous client system shutdown (also if the user click on X on the upper-right corner of the window)
    }
    
    
    //remote methods for client purpose via RMI
     @Override
    public void sendWords(String nickname, List<WordData> words) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String requestWordDef(WordData word) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ready(String nickname) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}