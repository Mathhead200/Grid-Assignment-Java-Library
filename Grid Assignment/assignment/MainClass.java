package assignment;

import grid.*;


public class MainClass
{

	public static void main(String[] args) throws GridException
	{
		Grid grid = new Grid(8, 8);

		Rock r = new Rock();
		r.addToBox( grid.getBoxAt(6, 6) );
		
		Sign s = new Sign( "This is such a cool Sign!" );
		grid.getBoxAt(1, 1).addGridItem(s);
		
		AlienTurtle t = new AlienTurtle(2);
		grid.getBoxAt(2, 5).addGridItem(t);
		
		Pawn p = new Pawn();
		grid.getBoxAt(4, 6).addGridItem(p);
		
		Hand me = new Hand();
		grid.getBoxAt(3, 4).addGridItem( me );
		
	}
}
