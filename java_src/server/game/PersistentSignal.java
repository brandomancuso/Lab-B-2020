 package server.game;


public class PersistentSignal {
    private boolean timeout,interruptFlag;
    private Game game;
    
    public PersistentSignal (Game game)
    {
        timeout=false;
        interruptFlag=false;
        this.game=game;
    }
    
    public synchronized void waitTimer() throws InterruptedException
    {
        while (!timeout)
        {
            wait();
        }
        
        timeout = false;

    }
    
    public synchronized void timeOver()
    {
        timeout = true;
        notify();
    }
    
    public synchronized void interruptGame ()
    {
        game.interrupt();
    }
    

}
