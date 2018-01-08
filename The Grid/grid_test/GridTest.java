package grid_test;

import com.mathhead200.grid.*;


public class GridTest
{
	public static void main(String[] args) throws Exception {
		BoxTemplate boxType = new BoxTemplate(16);
		Grid grid = new Grid( 15, 15, boxType );
		grid.getBoxAt(0, 0).addGridItem( new SimpleItem("A") );
		grid.getBoxAt(5, 7).addGridItem( new SimpleItem("B") );
		grid.getBoxAt(2, 9).addGridItem( new SimpleItem("C") );
		System.out.println( grid.getBoxAt(0, 0) );
		grid.overwrite("grid_test/save.jgs");

		Grid lg = Grid.load( "grid_test/save.jgs", Grid.CENTERED );
		lg.setTitle("Loaded Grid");
		System.out.println( lg.getBoxAt(0, 0) );
	}
}
