package server.Game;
import java.util.Observable;


public class Timer extends Observable implements Runnable 
{
    int time;//the variable time counts the seconds remained
    PersistentSignal persistentSignal;
    
    public Timer (int time,PersistentSignal persistentSignal)
    {
        this.time=time+1;
        this.persistentSignal=persistentSignal;
    }
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
        
        persistentSignal.signalON();//to warn the game that the time is over
    }
}
