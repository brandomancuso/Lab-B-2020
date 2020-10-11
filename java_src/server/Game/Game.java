package server.Game;

import server.Game.Dictionary;
import server.Game.InvalidKey;
import server.Game.Loader;
import java.io.File;
import java.io.IOException;

//Coustructor called after a master wants to create a Game
public final class Game{
    
    public Game ()
    {
        //TO-DO: give back the reference of the game just created
        AddPartecipant();//add the master of the game
    }
    
    //private methods for game class purpose
    private void StartGame()
    {
        
    }
    
    //public methods for server purpose
    public boolean AddPartecipant()
    {
        //TO-DO:add remote reference in the list of timer listeners and add player, before checking the maximum player number
        return true;
    }
    
    public boolean RemovePartecipant()
    {
        //TO-DO:remove remote reference in the list of timer listeners and remove player, and after checking the minimun player number got
        return true;
    }
    
    
    //remote methods for client purpose via RMI
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        Loader loader=new Loader();
		String file_dizionario= "dict-it.oxt";
		File dizionario=new File(file_dizionario);
		
		try {
			
			Dictionary d=loader.loadDictionaryFromFile(dizionario);
			
			/*for(String key: d.getKeys()) {
				System.out.println(d.getTerm(key));
			}*/
			System.out.println(d.getSize());
			System.out.println(d.getTerm("studente"));
			System.out.println(d.getTerm("studentato"));
			System.out.println(d.getTerm("studio"));
			System.out.println(d.getTerm("stud"));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKey e) {
			e.printStackTrace();
		}
    }  
}