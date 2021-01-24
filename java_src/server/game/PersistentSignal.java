 package server.game;

 /**
  * Class for syncronyzing the game with the timer
  * @author Christian Squadrito
  */

public class PersistentSignal {
    private boolean timeout,interruptFlag;
    private Game game;
    
    public PersistentSignal (Game game)
    {
        timeout=false;
        interruptFlag=false;
        this.game=game;
    }
    
    /**
     * this method is a point of syncronization for the game server while it's waiting for the timer timeout
     * @throws InterruptedException 
     */
    
    public synchronized void waitTimer() throws InterruptedException
    {
        while (!timeout)
        {
            wait();
        }
        
        timeout = false;

    }
    
    /**
     * This method allow to wake up the game server 
     */
    public synchronized void timeOver()
    {
        timeout = true;
        notify();
    }
    
    /**
     * This method allow to interrupt the game server
     */
    public synchronized void interruptGame ()
    {
        game.interrupt();
    }
    

}
