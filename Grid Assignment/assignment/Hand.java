package assignment;

import grid.*;


public class Hand extends MovableGridItem implements GridActor
{
	private GridItem myPalmItem;

	public Hand()
	{
		super("pics/hand.dmi", "Christopher D'Angelo");
	}


	public void bump(GridItem item) throws GridException
	{
		if( this.myPalmItem == null )
			this.pickUp(item);
	}

	public void keyDown(int k) throws GridException
	{
		switch(k) {
		//moving cases...
		case 97: //1
			this.moveSouthWest();
			break;
		case 98: //2
		case 40: //down
			this.moveSouth();
			break;
		case 99: //3
			this.moveSouthEast();
			break;
		case 100: //4
		case 37: //left
			this.moveWest();
			break;
		case 102: //6
		case 39: //right
			this.moveEast();
			break;
		case 103: //7
			this.moveNorthWest();
			break;
		case 104: //8
		case 38: //up
			this.moveNorth();
			break;
		case 105: //9
			this.moveNorthEast();
			break;
		//dropping item cases...
		case 87: //w
			this.dropItem(0, -1);
			break;
		case 65: //a
			this.dropItem(-1, 0);
			break;
		case 83: //s
			this.dropItem(0, 1);
			break;
		case 68: //d
			this.dropItem(1, 0);
			break;
		}
	}

	public void keyUp(int k)
	{
		//System.out.println( k );
	}


	public void moveNorth() throws GridException {
		this.move(0, -1);
	}

	public void moveNorthEast() throws GridException {
		this.move(1, -1);
	}

	public void moveEast() throws GridException {
		this.move(1, 0);
	}

	public void moveSouthEast() throws GridException {
		this.move(1, 1);
	}

	public void moveSouth() throws GridException {
		this.move(0, 1);
	}

	public void moveSouthWest() throws GridException {
		this.move(-1, 1);
	}

	public void moveWest() throws GridException {
		this.move(-1, 0);
	}

	public void moveNorthWest() throws GridException {
		this.move(-1, -1);
	}

	private void pickUp(GridItem item) throws GridException
	{
		item.removeFromGrid();
		this.myPalmItem = item;
	}

	public void dropItem(int x, int y) throws GridException {
		if( this.myPalmItem != null ) {
			try {
				int[] c = this.getParentBox().getBoxCord();
				this.getParentBox().getParentGrid().getBoxAt( c[0] + x, c[1] + y ).addGridItem( this.myPalmItem );
				this.myPalmItem = null;
			} catch(IndexOutOfBoundsException e) {}
		}
	}
}
