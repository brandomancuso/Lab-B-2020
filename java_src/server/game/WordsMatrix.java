 package server.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
 
 /**
  * 
  * @author Christian Squadrito
  */


public class WordsMatrix {
        //Letter Schema
        private final String[][] diceFaces=
        { 
            {"B","A","O","O","Qu","M"},
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
	private final static int DIM = 4;
	private final Dice[] dices;
	private final Dice[][] matrix;

	
	public WordsMatrix(){
		dices = new Dice[DIM*DIM];
		matrix = new Dice[DIM][DIM];
		for(int i = 0; i<DIM*DIM; i++) {
			dices[i] = new Dice(diceFaces[i]);
		}
                shake();
	}

//private method
	private void shake() {
		Random gen = new Random();
		int upperBound = DIM*DIM -1;
		for(int i = 0; i<DIM; i++) {
			for(int j = 0; j<DIM; j++) {
				int select = gen.nextInt(upperBound+1);
				matrix[i][j] = dices[select];
				dices[select].roll();
                                if ((select % 2) == 0)
                                    dices[select].roll();//to have more random element
				swap(select, upperBound--);//to exchage the last dice with the one selected in order to shaking
			}
		}
	}
        
        private void swap(int select, int bound) {
		Dice tmp = dices[bound];
		dices[bound] = dices[select];
		dices[select] = tmp;
	}

        private Stack<Coordinate> findLetter(Coordinate currentCoordinate,int indexLetter,String letter,Stack<Coordinate> cellToVisit,HashSet<Coordinate> cellVisited) 
    {
    	int x=currentCoordinate.getX(), y=currentCoordinate.getY();
		Coordinate coordinateTmp;
		
    	switch(currentCoordinate.getPosition())
    	{
    		case UPPER_LEFT:
    			for(int i=0;i<2;i++)
    	    		for(int j=1;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;

    		case UPPER:
    			for(int i=0;i<2;i++)
    	    		for(int j=-1;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    
    		case UPPER_RIGHT:
    			for(int i=0;i<2;i++)
    	    		for(int j=-1;j<1;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    
    		case CENTER_LEFT:
    			for(int i=-1;i<2;i++)
    	    		for(int j=0;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    	
    		case CENTER:
    			for(int i=-1;i<2;i++)
    	    		for(int j=-1;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    
    		case CENTER_RIGHT:
    			for(int i=-1;i<2;i++)
    	    		for(int j=-1;j<1;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    	
    		case LOWER_LEFT:
    			for(int i=-1;i<1;i++)
    	    		for(int j=0;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    
    		case LOWER:
    			for(int i=-1;i<1;i++)
    	    		for(int j=-1;j<2;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter) && (x!=x+i || y!=y+j))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	    	
    		case LOWER_RIGHT:
    			for(int i=-1;i<1;i++)
    	    		for(int j=-1;j<1;j++)
    	    		{
    	    			if (matrix[x+i][y+j].read().equals(letter))
    	    			{
    	    				coordinateTmp=new Coordinate(x+i, y+j,indexLetter ,DIM);
    	    				if (!cellVisited.contains(coordinateTmp))
    	    					cellToVisit.add(coordinateTmp);
    	    			}
    	    		}
    	    	break;
    	}
    	
    	return cellToVisit;
    }
        
//public method
     /**
      * get the grid of letter
      * @return String[] grid of letter
      */
    public String[] getWordsMatrix() {
        String[] gridTmp = new String[DIM * DIM];
        int count = 0;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                gridTmp[count] = matrix[i][j].read();
                count++;
            }
        }
        return gridTmp;
    }
	
    /**
     * This method is to know the derivability from the grid
     * @param wordFound the word to check
     * @return boolean it can or not be derivated from the grid
     */
    public boolean isAllowed (String wordFound)
     {
    	Stack<Coordinate> cellToVisit=new Stack<>();
    	HashSet<Coordinate> cellVisited=new HashSet<>();
    	char[] wordFoundArray=wordFound.toCharArray();
   	 	Coordinate currentCoordinate;
   	 	Boolean flag=false;
    	
    	//check the start cells in the all matrix
    	for(int i=0;i<DIM;i++)
    		for(int j=0;j<DIM;j++)
    		{
    			if(matrix[i][j].read().equals(String.valueOf(wordFoundArray[0])))
    				cellToVisit.add(new Coordinate(i, j,0,DIM));
    		}
    	
    	//search for a walkable paths	
        if(!cellToVisit.isEmpty())//maybe there isn't a start point
        {
         do
    		{ 	
        	 Stack<Coordinate> cellToVisitClone;
        	 for(int i=cellToVisit.peek().getIndexLetter()+1 ; i< wordFound.length() && !cellToVisit.isEmpty() ; i++)
        	 	{
	    			currentCoordinate=cellToVisit.pop();
	    			cellVisited.add(currentCoordinate);
	    			cellToVisitClone=(Stack<Coordinate>)cellToVisit.clone();
	    			cellToVisit=findLetter(currentCoordinate,i,String.valueOf(wordFoundArray[i]),cellToVisit,cellVisited);
	    			if(cellToVisit.equals(cellToVisitClone))
	    			{
	    				flag=false;
	    				i--;
	    				cellVisited.remove(currentCoordinate);//to remove the cell if it's wrong
	    			}
	    			else
	    				flag=true;
        	 	}
    		}while(!cellToVisit.isEmpty() && flag != true);
        }
         
         return flag;//if the stack is empty and any walkable paths were found, it means that the word composed is wrong
     }
    
    
}
    	

