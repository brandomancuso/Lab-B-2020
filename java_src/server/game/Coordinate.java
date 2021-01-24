package server.game;
 
import java.util.Objects;

/**
 * it design for the coordinate of the letter in the grid
 * @author Christian Squadrito
 */

public final class Coordinate {
	private final int x,y;//coordination of the letter inside GridLetter
	private final int indexLetter;//index inside the input word
	private Position position;//region of the letter inside GridLetter
	private final int dimMatrix;//length GridLetter
	
	public Coordinate (int x,int y,int indexLetter,int dimMatrix)
	{
		this.x=x;
		this.y=y;
		this.indexLetter=indexLetter;
		this.dimMatrix=dimMatrix;
		setPosition();
	}
	
	private void setPosition()
	{
		if ((x==0) && (y==0))
			position=Position.UPPER_LEFT;
		if ((x==0) && (y>=1))
			position=Position.UPPER;
		if ((x==0) && (y==dimMatrix-1))
			position=Position.UPPER_RIGHT;
		
		if ((x>=1) && (y==0))
			position=Position.CENTER_LEFT;
		if ((x>=1) && (y>=1))
			position=Position.CENTER;
		if ((x>=1) && (y==dimMatrix-1))
			position=Position.CENTER_RIGHT;
		
		if ((x==dimMatrix-1) && (y==0))
			position=Position.LOWER_LEFT;
		if ((x==dimMatrix-1) && (y>=1))
			position=Position.LOWER;
		if ((x==dimMatrix-1) && (y==dimMatrix-1))
			position=Position.LOWER_RIGHT;
	}
	
        /**
        * This method is to return the X-coordinate (index of column) of the GridLetter
        * @return int return the index of the column
        */
	public int getX()
	{
		return x;
	}
	
        /**
        * This method is to return the Y-coordinate (index of row) of the GridLetter
        * @return int return the index of the column
        */
	public int getY()
	{
		return y;
	}
	
        /**
        * This method is to return the index of the letter inside the input word to check.
        * @return int return the index of the letter
        */
	public int getIndexLetter()
	{
		return indexLetter;
	}
	
        /**
        * This method is to return the region where the letter is in the GridLetter
        * @return Position return the region
        * @see Position
        */
	public Position getPosition()
	{
		return position;
	}
        
	/**
         * Compare two object coordinate
         * @param obj the Coordinate to compare
         * @return boolean equal or not
         */
	@Override
	public boolean equals(Object obj) { 
		
		if (obj == this) 
			return true; 
		if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
		
		Coordinate objCoordinate = (Coordinate) obj;
		
		return((this.x==objCoordinate.getX())&&
		      (this.y==objCoordinate.getY())&&
		      (this.position==objCoordinate.getPosition()));
	}
	
        /**
         * Generate the hashCode
         * @return int the hash of the object Coordinate
         */
	@Override
	 public int hashCode() 
	 {
		return Objects.hash(this.x,this.y,this.position);
	 }
	
         /**
          * print the value of the coordinate
          * @return String print X and Y coordinate 
          */
	@Override
	 public String toString()
	 {
		return x+" "+y;
	 }
	
	
}
