package server;

import client.ClientServiceStub;
import entity.GameData;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WrappedObserver implements Observer{
    List<ClientServiceStub> clients;
    
    public WrappedObserver(){
        this.clients = new ArrayList<>();
    }
    
    public void addRemoteObserver(ClientServiceStub remoteClient){
        this.clients.add(remoteClient);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        List<GameData> games = (List<GameData>)arg;
        try{
           for(ClientServiceStub c : clients){
               c.update(games);
           }
        }
        catch(RemoteException e){
            o.deleteObserver(this);
        }
    }   
}
