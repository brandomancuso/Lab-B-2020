package server.game;

import java.util.Random;

public class Dice {      
	private static int DIM = 6;
	private String[] sides;
	private String side_up;
	
	public Dice(String[] values) {
		sides = new String[DIM];
		for(int i = 0; i < DIM; i++) {
			sides[i] = values[i];
		}
		side_up = null;
	}
	
	public void roll() {
		Random gen = new Random();
		int randomSide = gen.nextInt(DIM);
		side_up = sides[randomSide];
	}
	
	public String read() {
		return side_up;
	}
}
