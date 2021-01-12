 package server.game;

import java.util.Observable;
import java.util.Observer;
import client.ClientGameStub;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
 
/**
 * 
 * @author Christian Squadrito
 */

public class ObserverClient implements Observer  {
    private final ClientGameStub client;
    private List<String> wordsFound;
    private final Game game;//to have access to forcedExit method
    private final String nickname;//i need to display the nickname of the person who forcedExit the game
    
    public ObserverClient (String nickname,ClientGameStub client,Game game,Timer timer)
    {
        wordsFound=new ArrayList<>();
        this.nickname=nickname;
        this.client=client;
        this.game=game;
        timer.addObserver(this);//the reference isn't saved because i have just to add me as observer
    }
    
    /**
     * The method to update the client about the changement timer's state
     * @param o the timer observable
     * @param arg the time of the timer
     */
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            client.updateTimer((int) arg);
        } catch (RemoteException ex) {
            System.err.println("Client isn't reachable");
            o.deleteObserver(this);
        }
    }
    
    /**
     * get the remote object of the client player
     * @return remote object of the client player
     */

    public ClientGameStub getClientGameStub()
    {
        return client;
    }
    
    /**
     * set the list of the word found
     * @param wordsFound 
     */
       
    public void setWordsFound(ArrayList<String> wordsFound)
    {
        this.wordsFound=wordsFound;
    }
    
    /**
     * get the nickname of the remote client player
     * @return nickname of the remote client player
     */
    
    public String getNickname ()
    {
        return nickname;
    }
    
    /**
     * get the list of the word found
     * @return list of the word found
     */
    public List<String> getWordsFound ()
    {
        return wordsFound;
    }
}
