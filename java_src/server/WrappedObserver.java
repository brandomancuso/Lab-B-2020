package server;

import client.ClientServiceStub;
import entity.GameData;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WrappedObserver implements Observer{
    ClientServiceStub client;
    
    public WrappedObserver(ServerServiceImpl server, ClientServiceStub client){
        server.addObserver(this);
        this.client = client;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try{
           client.update((List<GameData>)arg);
        }
        catch(RemoteException e){
            o.deleteObserver(this);
            ((ServerServiceImpl)o).removeObserver(client);
        }
    }   
}
