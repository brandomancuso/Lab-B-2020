package server.Game;

public class PersistentSignal {
    private boolean timeout,interruptFlag;
    
    public PersistentSignal ()
    {
        timeout=false;
        interruptFlag=false;
    }
    
    public synchronized void waitTimer() throws InterruptedException
    {
        while (!timeout)
        {
            wait();
        }
        
        if(interruptFlag)
            Thread.currentThread().interrupt();
        timeout = false;

    }
    
    public synchronized void signalON()
    {
        timeout = true;
        notify();
    }
    
    public synchronized void interruptGame ()
    {
        timeout=true;
        interruptFlag=true;
        notify();
    }
    

}
