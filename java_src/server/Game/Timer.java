import java.util.Observable;
import java.util.Observer;


public class Timer extends Observable implements Runnable 
{
    int time;//the variable time counts the seconds remained
    
    public Timer (int time)
    {
        this.time=time+1;//adding 1 unit when it turns into 30 than it notifies a setChanged()
        //TO-DO:start the thread when all the clients receive the remote reference
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
    }
    
}
