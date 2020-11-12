package client;

import entity.GameData;
import entity.StatsData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Edoardo
 */
public class ClientServiceImpl extends UnicastRemoteObject implements ClientServiceStub {

    public StatsData statsData;
    public List<GameData> gamesList;

    public ClientServiceImpl() throws RemoteException {

    }

    @Override
    public synchronized void update(StatsData data) throws RemoteException {
            statsData = data;
    }

    @Override
    public synchronized void update(List<GameData> games) throws RemoteException {
        gamesList = games;
    }

    @Override
    public ClientGameStub getGameStub() throws RemoteException {
        return null;

    }

}
