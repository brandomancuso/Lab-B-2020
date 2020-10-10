package client;

import entity.GameData;
import entity.StatsData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientServiceStub extends Remote{
    void update(StatsData data) throws RemoteException;
    void update(List<GameData> games) throws RemoteException;
    ClientGameStub getGameStub() throws RemoteException;
}
