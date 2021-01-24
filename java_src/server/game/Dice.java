 package server.game;

import java.util.Random;

 /**
  * it's the dice to generate the letter inside the grid
  * @author Christian Squadrito
  */
public class Dice {      
	private static int DIM = 6;
	private String[] sides;//the faces of the dice
	private String side_up;//the face come out after throwing
	
	public Dice(String[] values) {
		sides = new String[DIM];
		for(int i = 0; i < DIM; i++) {
			sides[i] = values[i];
		}
		side_up = null;
	}
	
       /**
        * rool the dice to simulate a throwing
        */
	public void roll() {
		Random gen = new Random();
		int randomSide = gen.nextInt(DIM);
		side_up = sides[randomSide];
	}
	
        /**
         * print the letter come out after throwing
         * @return String the letter
         */
	public String read() {
		return side_up;
	}
}
