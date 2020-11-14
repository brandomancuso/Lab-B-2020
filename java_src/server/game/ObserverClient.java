package server.game;

import java.util.Observable;
import java.util.Observer;
import client.ClientGameStub;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ObserverClient implements Observer  {
    private final ClientGameStub client;
    private List<String> wordsFound;
    private final Game game;//to have access to exit method
    private final String nickname;//i need to display the nickname of the person who exit the game
    private Timer timer;//to have access to the method of observable
    
    public ObserverClient (String nickname,ClientGameStub client,Game game,Timer timer)
    {
        wordsFound=new ArrayList<>();
        this.nickname=nickname;
        this.client=client;
        this.game=game;
        this.timer=timer;
        timer.addObserver(this);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            client.update((int) arg);
        } catch (RemoteException ex) {
            System.err.println("Client isn't reachable");
            o.deleteObserver(this);
            game.exit();
        }
    }

    public ClientGameStub getClientGameStub()
    {
        return client;
    }
       
    public void setWordsFound(ArrayList<String> wordsFound)
    {
        this.wordsFound=wordsFound;
    }
    
    public String getNickname ()
    {
        return nickname;
    }
    
    public List<String> getWordsFound ()
    {
        return wordsFound;
    }
}
