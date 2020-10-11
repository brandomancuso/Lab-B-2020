package server.Game;
import java.util.Observable;
import java.util.Observer;


public class Timer extends Observable implements Runnable 
{
    int time;//the variable time counts the seconds remained
    
    public Timer (int time)
    {
        this.time=time;
        //TO-DO:start the thread when all the clients receive the remote reference
    }
    @Override
    public void run() {
        float clockTmpInSeconds=(System.currentTimeMillis()/1000);//to know when a second is clicked
        
        while(time!=0 & true)
        {
            if(clockTmpInSeconds==(System.currentTimeMillis()/1000))
            {
                try {
                        Thread.sleep(300);
                } catch (InterruptedException ex) {
                    break;//in case someone wants to stop the timer
                }
            }
            else
            {
                time--;//to reduce the time by one unit after checking a second has just clicked
                setChanged();
                notifyObservers(time);//notify to all listeners the new state of the timer
            }
        }
        
        System.out.println("The Timer is arrived to 0!");
    }
    
}
