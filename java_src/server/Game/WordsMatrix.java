package server.Game;
import java.util.Random;

public class WordsMatrix {
        //Letter Schema
        private String[][] letter=
        { 
            {"B","A","O","O","Q","M"},
            {"U","T","E","S","L","P"},
            {"I","G","E","N","V","T"},
            {"O","U","L","I","E","R"},
            {"A","C","E","S","L","R"},
            {"R","A","T","I","B","L"},
            {"S","M","I","R","O","A"},
            {"I","S","E","E","F","H"},
            {"S","O","T","E","N","D"},
            {"A","I","C","O","F","R"},
            {"V","N","Z","D","A","E"},
            {"I","E","A","T","A","O"},
            {"O","T","U","C","E","N"},
            {"N","O","L","G","U","E"},
            {"D","C","M","P","A","E"},
            {"E","R","I","N","S","H"}
        };  
	private static int DIM = 4;
	private Dice[] dices;
	private Dice[][] matrix;
	
	public WordsMatrix(){
		dices = new Dice[DIM*DIM];
		matrix = new Dice[DIM][DIM];
		for(int i = 0; i<DIM*DIM; i++) {
			dices[i] = new Dice(letter[i]);
		}
	}
	
	public void shake() {
		Random gen = new Random();
		int upperBound = DIM*DIM -1;
		for(int i = 0; i<DIM; i++) {
			for(int j = 0; j<DIM; j++) {
				int select = gen.nextInt(upperBound+1);
				matrix[i][j] = dices[select];
				dices[select].roll();
				swap(select, upperBound--);//to exchage the last dice with the one selected in order to shaking
			}
		}
	}
	
	public String[] print() {
            String[] gridTmp=new String[DIM*DIM];
            int count=1;
		for(int i = 0; i<DIM; i++) {
			System.out.println();
			for(int j = 0; j<DIM; j++) {
				gridTmp[count]=matrix[i][j].read();
                                count++;
			}
		}
            return gridTmp;
	}
        
        public boolean isAllowed (String wordFound)
        {
            //check if the word could be derived from the matrix
            return true;
        }

	private void swap(int select, int bound) {
		Dice tmp = dices[bound];
		dices[bound] = dices[select];
		dices[select] = tmp;
	}
}
