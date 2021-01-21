package client;

import entity.GameData;
import entity.StatsData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * interfaccia ClientService
 * @author Edoardo
 */
public interface ClientServiceStub extends Remote{
    
    /**
     * aggiornamento delle statistiche nella grafica
     * @param data statistiche
     * @throws RemoteException 
     */
    void update(StatsData data) throws RemoteException;
    
    /**
     * aggiornamento delle partite in lista
     * @param games lista di partite
     * @throws RemoteException 
     */
    void update(List<GameData> games) throws RemoteException;
    
    /**
     * avvisa il client che il server è stato spento e chiude il client
     * @throws RemoteException 
     */
    void shutDownServer() throws RemoteException;
}
