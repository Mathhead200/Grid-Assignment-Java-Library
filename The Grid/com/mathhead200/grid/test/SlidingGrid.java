package com.mathhead200.grid.test;

import com.mathhead200.grid.*;
import java.awt.*;

/**
 * not working
 * @author Christopher
 */
public class SlidingGrid
{
	private Box[][] boxes;
	private Grid grid;
	private int top;
	private int left;

	public SlidingGrid(int x, int y, int gridX, int gridY, BoxTemplate t, int offsetX, int offsetY)
	{
		grid = new Grid(gridX, gridY, t);
		top = offsetY;
		left = offsetX;

		if( gridX + left > x )
			x = gridX + left;
		if( gridY + top > y )
			y = gridY + top;

		boxes = new Box[x][y];
		for( int i = 0; i < x; i++ ) {
			for( int j = 0; j < y; j++ ) {
				if( i < gridX + left && j < gridY + top )
					boxes[i][j] = grid.getBoxAt(i, j);
				else {
					boxes[i][j] = t.makeBox();
				}
			}
		}
	}

	public SlidingGrid(int x, int y, int gridX, int gridY, BoxTemplate t) {
		this(x, y, gridX, gridY, t, 0, 0);
	}

	public SlidingGrid(int x, int y, int gridX, int gridY) {
		this( x, y, gridX, gridY, new BoxTemplate() );
	}


	public Box[][] getBoxes() {
		return boxes;
	}

	public Box getBoxAt(int x, int y) {
		return boxes[x][y];
	}

	public Grid getGrid() {
		return grid;
	}

	public int getTop() {
		return top;
	}

	public int getLeft() {
		return left;
	}


	public void slide(int right, int down) {
		updateBoxes();
		for( int i = 0; i < grid.getBoxes().length; i++ ) {
			for( int j = 0; j < grid.getBoxes()[0].length; j++ ) {
				grid.setBoxAt( boxes[left + right + i][top + down + j], i, j );
			}
		}
		left += right;
		top += down;
	}

	private void updateBoxes() {
		for( int i = 0; i < grid.getBoxes().length; i++ ) {
			for( int j = 0; j < grid.getBoxes()[0].length; j++ ) {
				boxes[left + i][top + j] = grid.getBoxAt(i, j);
			}
		}
	}


	public static void main(String...a) throws GridException {
		SlidingGrid g = new SlidingGrid(10, 10, 5, 5);
		g.getGrid().getBoxAt(2, 2).addGridItem( new com.mathhead200.grid.griditems.Bug("grid/griditems/beetle.dmi", "programming Bug") );
		g.slide(1, 1);
	}
}
