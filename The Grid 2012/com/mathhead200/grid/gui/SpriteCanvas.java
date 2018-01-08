package com.mathhead200.grid.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.mathhead200.grid.Coord;
import com.mathhead200.grid.Grid;


public class SpriteCanvas extends Canvas
{
	private static final long serialVersionUID = -1128780458016425766L;

	private Grid<? extends Sprite> grid;
	private int columns;
	private int rows;
	private int spriteWidth;
	private int spriteHeight;
	private Coord eye;

	private BufferedImage buffer;

	private void updateSize() {
		buffer = new BufferedImage( columns * spriteWidth, rows * spriteHeight, BufferedImage.TYPE_INT_ARGB );
		setMinimumSize( new Dimension(buffer.getWidth(), buffer.getHeight()) );
		setSize( getMinimumSize() );
	}

	public SpriteCanvas(Grid<? extends Sprite> grid, int columns, int rows, int spriteWidth, int spriteHeight) {
		this.grid = grid;
		this.columns = columns;
		this.rows = rows;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.eye = new Coord(0, 0);
		updateSize();
	}

	public void setView(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		updateSize();
	}

	public void setSpriteSize(int width, int height) {
		spriteWidth = width;
		spriteHeight = height;
		updateSize();
	}

	public void setEye(Coord eye) {
		this.eye = eye;
	}

	public void paint(Graphics g) {
		g.drawImage(buffer, 0, 0, getBackground(), null);
	}

	public void update(Graphics g) {
		Graphics2D bufG = buffer.createGraphics();
		for( int x = 0; x < columns; x++ )
			for( int y = 0; y < rows; y++ ) {
				Coord c = new Coord( eye.x + x, eye.y + y );
				Sprite sprite = grid.get(c);
				if( sprite == null )
					continue;
				bufG.drawImage( sprite.getImage(), x * spriteWidth, (rows - 1 - y) * spriteWidth, null );
			}
		paint(g);
	}
}
