 package server.game;

import java.util.Observable;
import java.util.Observer;
import client.ClientGameStub;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.JOptionPane.showMessageDialog;

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
    
    @Override
    public void update(Observable o, Object arg) {
        try {
            client.updateTimer((int) arg);
        } catch (RemoteException ex) {
            System.err.println("Client isn't reachable");
            o.deleteObserver(this);
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
