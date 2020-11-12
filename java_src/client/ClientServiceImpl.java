package client;

import entity.GameData;
import entity.StatsData;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edoardo
 */
public class ClientServiceImpl extends UnicastRemoteObject implements ClientServiceStub {

    //SINGLETON
    private static ClientServiceImpl csiSingleInstance = null;

    private StatsData statsData;
    private List<GameData> gamesList;

    private ClientServiceImpl() throws RemoteException {
        statsData = new StatsData();
        gamesList = new ArrayList<GameData>();
    }

    public static ClientServiceImpl ClientServiceImpl() {
        if (csiSingleInstance == null) {
            try {
                csiSingleInstance = new ClientServiceImpl();
            } catch (RemoteException ex) {
                Logger.getLogger(ClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return csiSingleInstance;
    }

    //GETTER
    public synchronized StatsData getStatsData() {
        return statsData;
    }

    public synchronized List<GameData> getGamesList() {
        return gamesList;
    }

    //METHODS
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
