package com.mathhead200.grid13.example;

import com.mathhead200.grid13.Grid;
import com.mathhead200.grid13.util.AngleType;
import com.mathhead200.grid13.util.BufferedSprite;
import com.mathhead200.grid13.util.GUI;


public class BasicGUIExample
{
	public static void main(String[] argv) {
		//create our grid
		Grid grid = new Grid();

		//load our sprites from file
		BufferedSprite item1 = GUI.loadSprite("bandit2.dmi");
		BufferedSprite item2 = GUI.loadSprite("bandit3.dmi");
		BufferedSprite item3 = GUI.loadSprite("dog.dmi");
		BufferedSprite item4 = GUI.loadSprite("big.png");

		//mess with the sprites a bit
		item1.rotate(90, AngleType.DEGREE);
		item1.translate(32, 0);
		item2.scale(-1, 1);
		item2.translate(32, 0);
		item3.shear(0, .25);
		item3.translate(0, -8);

		//add the sprites to the grid, each at some different location
		grid.add(item1, 1, 6);
		grid.add(item2, 4, 4);
		grid.add(item3, 8, 3);
		grid.add(item4, 0, 0);

		//move items
		item1.moveBy(1, 1);

		//display are grid in a window
		GUI.displayGrid(grid, 10, 10);
	}
}
