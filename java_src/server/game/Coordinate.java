
 package server.game;
 
import java.util.Objects;

public final class Coordinate {
	private final int x,y;
	private final int indexLetter;
	private Position position;
	private final int dimMatrix;
	
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
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getIndexLetter()
	{
		return indexLetter;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
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
	
	@Override
	 public int hashCode() 
	 {
		return Objects.hash(this.x,this.y,this.position);
	 }
	
	@Override
	 public String toString()
	 {
		return x+" "+y;
	 }
	
	
}
