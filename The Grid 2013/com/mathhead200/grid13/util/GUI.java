package com.mathhead200.grid13.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mathhead200.grid13.Coordinate;
import com.mathhead200.grid13.Grid;
import com.mathhead200.grid13.gui.GridCanvas;
import com.mathhead200.grid13.gui.Sprite;


public final class GUI
{
	public static final Deque<Exception> exceptionDeque = new ArrayDeque<Exception>(1);

	/**
	 * Use to quickly display any grid, though only {@link Sprite} items will be displayed.
	 *
	 * @param grid - the grid to display
	 * @param cols - number of columns to display
	 * @param rows - number of rows to display
	 * @param cellWidth - the number of pixels wide, for each item
	 * @param cellHeight - the number of pixels tall, for each item
	 * @param eye - the Coordinate of the upper-left corner, default: (0,0)
	 *
	 * @return the {@link JFrame} created to show the given grid; it can be ignored if not needed.
	 */
	public static JFrame displayGrid(Grid grid, int cols, int rows, int cellWidth, int cellHeight, Coordinate eye) {
		JFrame frame = new JFrame("The Grid 2013");
		GridCanvas canvas = new GridCanvas(grid, cols, rows, cellWidth, cellHeight);
		canvas.eye = eye;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2 );
		frame.setVisible(true);
		return frame;
	}

	/**
	 * Use to quickly display any grid, though only {@link Sprite} items will be displayed.
	 *
	 * @param grid - the grid to display
	 * @param cols - number of columns to display
	 * @param rows - number of rows to display
	 * @param cellWidth - the number of pixels wide, for each item
	 * @param cellHeight - the number of pixels tall, for each item
	 *
	 * @return the {@link JFrame} created to show the given grid; it can be ignored if not needed.
	 */
	public static JFrame displayGrid(Grid grid, int cols, int rows, int cellWidth, int cellHeight) {
		return displayGrid(grid, cols, rows, cellWidth, cellHeight, Coordinate.ORIGIN);
	}

	/**
	 * Use to quickly display any grid, though only {@link Sprite} items will be displayed.
	 *
	 * @param grid - the grid to display
	 * @param cols - number of columns to display
	 * @param rows - number of rows to display
	 *
	 * @return the {@link JFrame} created to show the given grid; it can be ignored if not needed.
	 */
	public static JFrame displayGrid(Grid grid, int cols, int rows) {
		return displayGrid(grid, cols, rows, 32, 32);
	}

	/**
	 * Use to quickly display any grid, though only {@link Sprite} items will be displayed.
	 *
	 * @param grid - the grid to display
	 *
	 * @return the {@link JFrame} created to show the given grid; it can be ignored if not needed.
	 */
	public static JFrame displayGrid(Grid grid) {
		return displayGrid(grid, 10, 10);
	}

	/**
	 * @see #downloadSprite(String)
	 *
	 * @param filename - name and path to where the (local) sprite image file can be found
	 *
	 * @return the {@link Sprite} if the load was successful, otherwise <code>null</code>
	 */
	public static BufferedSprite loadSprite(String filename) {
		try {
			return new BufferedSprite( ImageIO.read(new File(filename)) );
		} catch(IOException e) {
			exceptionDeque.addLast(e);
			return null;
		}
	}

	/**
	 * @see #loadSprite(String)
	 *
	 * @param url - the URL address where the sprite image file can be found
	 *
	 * @return the {@link Sprite} if the download was successful, otherwise <code>null</code>
	 */
	public static BufferedSprite downloadSprite(String url) {
		try {
			return new BufferedSprite( ImageIO.read(new URL(url)) );
		} catch(IOException e) {
			exceptionDeque.addLast(e);
			return null;
		}
	}
}
