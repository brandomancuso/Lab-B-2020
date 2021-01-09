package server;

import client.ClientServiceStub;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe per gestire il pattern observer localmente comunicando con gli oggetti remoti del client
 * @author Fedeli Andrea
 * @see java.util.Observer
 */
public class WrappedObserver implements Observer{
    ClientServiceStub client;
    ServerServiceImpl server;
    String nickname;
    
    /**
     * Costruttore della classe
     * @param server Oggetto server da osservare
     * @param client Oggetto remoto del client
     * @param nick Nickname dell'utente
     */
    public WrappedObserver(ServerServiceImpl server, ClientServiceStub client, String nick){
        this.server = server;
        this.client = client;
        this.nickname = nick;
        
        this.server.addObserver(this);
    }
    
    /**
     * Metodo per aggiornare i client remoti
     * @param o Oggetto osservabile
     * @param arg Parametro per determinara che cosa inviare al client
     * @see java.util.Observable
     */
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
