package assignment;

import grid.*;


public class AlienTurtle extends MovableGridItem
{
	private int mySpeed;

	public AlienTurtle(int speed)
	{
		super( "pics/turtle.dmi", "glib'zorb" );
		mySpeed = speed;
	}


	protected void bump(GridItem item) throws GridException {
		item.kill();
		mySpeed--;
	}


	public void moveNorth() throws GridException {
		move(0, -mySpeed);
	}

	public void moveEast() throws GridException {
		move(mySpeed, 0);
	}

	public void moveSouth() throws GridException {
		move(0, mySpeed);
	}

	public void moveWest() throws GridException {
		move(-mySpeed, 0);
	}
}
