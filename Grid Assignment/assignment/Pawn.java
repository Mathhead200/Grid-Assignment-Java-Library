package assignment;

import grid.*;


public class Pawn extends MovableGridItem
{
	boolean isFirstMove;

	public Pawn() {
		super( "pics/pawn.dmi", "White Pawn" );
		isFirstMove = true;
	}


	protected void bump(GridItem item) {}

	public void moveMe() throws GridException {
		move(0, -1);
	}

	public void moveMe(int n) throws GridException
	{
		if( isFirstMove && n==2 ) {
			moveMe();
			moveMe();
		}
		else if( n==1 )
			moveMe();
		else
			System.out.println("Hey! You can't do that!");
	}
	
	/*
	public void moveMe(int n) throws GridException
	{
		if( isFirstMove && n==2 ) {
			move(0, -1);
			move(0, -1);
		}
		else if( n==1 )
			move(0, -1);
		else
			System.out.println("Hey! You can't do that!");
	}
	*/
}
