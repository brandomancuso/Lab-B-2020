package server.Game;

import java.io.File;
import java.io.IOException;

public class MainServer {
    
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
