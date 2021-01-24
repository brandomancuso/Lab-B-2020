 package server.game;
import java.util.Observable;
 /**
  * it's the game timer
  * @author Christian Squadrito
  */


public class Timer extends Observable implements Runnable 
{
    private int time;//the variable time counts the seconds remained
    private final PersistentSignal persistentSignal;
    
    public Timer (PersistentSignal persistentSignal)
    {
        this.persistentSignal=persistentSignal;
    }
    
    /**
     * set the time
     * @param time 
     */
    
    public void setTime (int time)
    {
        this.time=time;
    }
    
    /**
     * is the run of the timer thread
     */
    
    @Override
    public void run() {
        int clockTmpInSeconds=(int)(System.currentTimeMillis()/1000);//to know when a second is clicked
        
        while(time!=0 & true)
        {
            if(clockTmpInSeconds==(int)(System.currentTimeMillis()/1000))
            {
                try {
                        Thread.sleep(100);
                } catch (InterruptedException ex) {
                    break;//in case someone wants to stop the timer
                }
            }
            else
            {
                time--;//to reduce the time by one unit after checking a second has just clicked
                clockTmpInSeconds=(int)(System.currentTimeMillis()/1000);//to reset the clockTmp with the new second
                setChanged();
                notifyObservers(time);//notify to all listeners the new state of the timer
            }
        }
        
        persistentSignal.timeOver();//to warn the game that the time is over
    }
}
