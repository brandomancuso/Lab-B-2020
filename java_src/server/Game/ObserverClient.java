package server.Game;

import java.util.Observable;
import java.util.Observer;


public class ObserverClient implements Observer  {

    Timer timer;
    Object client;
    
    public ObserverClient (Timer timer,Object client)
    {
        this.timer=timer;
        this.client=client;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
