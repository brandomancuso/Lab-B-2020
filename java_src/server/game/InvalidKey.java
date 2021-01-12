 package server.game;

 /**
  * 
  * @author Christian Squadrito
  */

public class InvalidKey extends Exception {

		private static final long serialVersionUID = 1L;
		
		public InvalidKey(){
			super("Parola non contenuta nel dizionario");
		}

	}
