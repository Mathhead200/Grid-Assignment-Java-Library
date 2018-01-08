package com.mathhead200.grid13.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.mathhead200.grid13.Coordinate;
import com.mathhead200.grid13.Grid;
import com.mathhead200.grid13.GridItem;


@SuppressWarnings("serial")
public class GridCanvas extends Canvas
{
	public final Grid grid;
	public int cols;
	public int rows;
	public int cellWidth;
	public int cellHeight;
	public Coordinate eye = Coordinate.ORIGIN;


	public GridCanvas(Grid grid, int cols, int rows, int cellWidth, int cellHeight) {
		this.grid = grid;
		this.cols = cols;
		this.rows = rows;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		setSize( cellWidth * cols, cellHeight * rows );
		setBackground(Color.WHITE);
	}


	public void paint(Graphics g) {
		final int width = getWidth() / cols; //width in pixels of each box
		final int height = getHeight() / rows; //height in pixels of each box
		g.setColor( getBackground() );
		g.fillRect( 0, 0, getWidth(), getHeight() );
		for( int y = 0; y < rows; y++ )
			for( int x = 0; x < cols; x++ ) {
				GridItem item = grid.getAt( eye.x + x, eye.y + y );
				if( !(item instanceof Sprite) )
					continue;
				Sprite sprite = (Sprite)item;
				Image img = sprite.getImage();
				final int imgWidth = width * img.getWidth(null) / cellWidth;
				final int imgHeight = height * img.getHeight(null) / cellHeight;
				g.drawImage( img, x * width, y * height, imgWidth, imgHeight, null );
			}
	}
}
