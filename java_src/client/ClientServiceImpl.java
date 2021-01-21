package client;

import entity.GameData;
import entity.StatsData;
import java.awt.Component;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;

/**
 * implementazione ClientServiceStub
 *
 * @author Edoardo
 */
public class ClientServiceImpl extends UnicastRemoteObject implements ClientServiceStub {

    private static ClientServiceImpl csiSingleInstance = null;
    private StatsData statsData;
    private List<GameData> gameList;
    private ControlFrame gui;

    private ClientServiceImpl(ControlFrame parGui) throws RemoteException { 
        super(0);
        statsData = new StatsData();
        gameList = new ArrayList<GameData>();
        this.gui = parGui;
    }

    public static ClientServiceImpl ClientServiceImpl(ControlFrame parGui) {
        if (csiSingleInstance == null) {
            try {
                csiSingleInstance = new ClientServiceImpl(parGui);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return csiSingleInstance;
    }

    /**
     * ritorna oggetto relativo alle statistiche
     * @return 
     */
    public synchronized StatsData getStatsData() {
        return statsData;
    }

    /**
     * ritorna lista di partite
     * @return 
     */
    public synchronized List<GameData> getGamesList() {
        return gameList;
    }

    /**
     * aggiornamento delle statistiche nella grafica
     *
     * @param data statistiche
     * @throws RemoteException
     */
    @Override
    public synchronized void update(StatsData data) throws RemoteException {
        statsData = data;
    }

    /**
     * aggiornamento delle partite in lista
     *
     * @param games lista di partite
     * @throws RemoteException
     */
    @Override
    public synchronized void update(List<GameData> games) throws RemoteException {
        gameList = games;
        gui.fillGameTable(gameList);
        //capire quando sono nella finestra home e chiamare il metodo per refillare la tabella partite

    }

    /**
     * avvisa il client che il server è stato spento e chiude il client
     *
     * @throws RemoteException
     */
    @Override
    public void shutDownServer() throws RemoteException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.shutdownServer();
            }
        });
    }

}
