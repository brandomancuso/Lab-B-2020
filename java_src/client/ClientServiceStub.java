package client;

import entity.GameData;
import entity.StatsData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientServiceStub extends Remote{
    //creare due var una per stast e una lista partita, vengono settate dopo il login dal server chiamando i due metodo qui presenti. 
    //dopo nelle finestre uso quelle variabili --> sincronizzate
    void update(StatsData data) throws RemoteException; //aggiornamento statistiche nella finestra statistiche --> logica x visualizzare stats nella finestra
    void update(List<GameData> games) throws RemoteException;//mi passa le partite da mettere nella lista --> logica x aggiungere partita alla lista
    void shutDownServer() throws RemoteException;
}
