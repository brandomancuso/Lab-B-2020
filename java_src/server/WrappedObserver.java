package server;

import client.ClientServiceStub;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

public class WrappedObserver implements Observer{
    ClientServiceStub client;
    ServerServiceImpl server;
    String nickname;
    
    public WrappedObserver(ServerServiceImpl server, ClientServiceStub client, String nick){
        this.server = server;
        this.client = client;
        this.nickname = nick;
        
        this.server.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try{
            if((boolean)arg){
                client.update(server.getStats());
            }
            client.update(server.getGamesList());
        }
        catch(RemoteException e){
            o.deleteObserver(this);
            ((ServerServiceImpl)o).removeObserver(nickname);
        }
    }
}
