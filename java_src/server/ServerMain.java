package server;

import java.rmi.RemoteException;

public class ServerMain {
    public void main(String[] args) throws RemoteException{
        ServerServiceImpl server = new ServerServiceImpl();
    }
}
