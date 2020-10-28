package server.Game;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import client.ClientGameStub;
import java.rmi.RemoteException;

public class ObserverClient implements Observer  {

    Timer timer;
    ClientGameStub client;
    Game game;
    
    public ObserverClient (Timer timer,ClientGameStub client,Game game)
    {
        this.timer=timer;
        this.client=client;
        this.game=game;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            client.update((int) arg);
        } catch (RemoteException ex) {
            System.err.println("Client isn't reachable");
            o.deleteObserver(this);
            game.exit(this);
        }
    }
    
    @Override
    public int hashCode() 
	 {
            return Objects.hash(this.client);
	 }    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObserverClient other = (ObserverClient) obj;
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        return true;
    }
}
