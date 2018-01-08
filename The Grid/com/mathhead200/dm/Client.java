package com.mathhead200.dm;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;


@SuppressWarnings("serial")
public class Client extends Canvas
{
	//Member Variables
	private Dim view;

	public World world;
	public Atom eye;
	public int pixelX = 0;
	public int pixelY = 0;
	public Mob mob = null;

 //Private Methods
	private void drawAtom(Atom atom, Graphics g, int x, int y) {
		int xDraw = x * world.getIconSize().width + atom.pixelX,
			yDraw = y * world.getIconSize().height + atom.pixelY;
		if( -world.getIconSize().width < xDraw && xDraw < (world.getMapx() + 1) * world.getIconSize().width
			&& -world.getIconSize().height < yDraw && yDraw < (world.getMapy() + 1) * world.getIconSize().height
		)
			g.drawImage( atom.icon, xDraw, yDraw,
				world.getIconSize().width, world.getIconSize().height, null );
	}

 //Constructors
	public Client(World world) {
		this.world = world;
		eye = world.locate(0, 0, 0);
		view = new Dim(11, 11);
		setBackground(Color.BLACK);
		setSize( view.width * world.getIconSize().width, view.height * world.getIconSize().height );
	}

	public Client() {
		this(null);
	}

 //Accessor Methods
	public Dim getView() {
		return view;
	}

 //Mutator Methods
	public void setView(Dim view) {
		this.view = view;
		setSize( view.width * world.getIconSize().width, view.height * world.getIconSize().height );
	}

	public void setView(int width, int height) {
		setView( new Dim(width, height) );
	}

 //Overriden Methods
	public void paint(Graphics g) {
		if( world == null || view.width < 0 || view.height < 0 )
			return;
		for( int x = 0; x < world.getMapx(); x++ )
			for( int y = 0; y < world.getMapy(); y++ )
				drawAtom( world.squares[x][y][eye.getZ()].area, g, x, y );
		for( int x = 0; x < world.getMapx(); x++ )
			for( int y = 0; y < world.getMapy(); y++ )
				drawAtom( world.squares[x][y][eye.getZ()].turf, g, x, y );
		for( int x = 0; x < world.getMapx(); x++ )
			for( int y = 0; y < world.getMapy(); y++ )
				for( Obj obj : world.squares[x][y][eye.getZ()].objs )
					drawAtom( obj, g, x, y );
		for( int x = 0; x < world.getMapx(); x++ )
			for( int y = 0; y < world.getMapy(); y++ )
				for( Mob mob : world.squares[x][y][eye.getZ()].mobs )
					drawAtom( mob, g, x, y );
		int outBoundLeft = -world.getIconSize().width,
			outBoundTop = -world.getIconSize().height,
			outBoundRight = (world.getMapx() + 1) * world.getIconSize().width,
			outBoundBottom = (world.getMapy() + 1) * world.getIconSize().height,
			inBoundLeft = Math.max( (eye.getX() - view.width / 2 + pixelX) * world.getIconSize().width, 0 ),
			inBoundTop = Math.max( (eye.getY() - view.height / 2 + pixelY) * world.getIconSize().height, 0 ),
			inBoundRight = Math.min( (eye.getX() + (int)Math.ceil( view.width / 2.0 ) + pixelX) * world.getIconSize().width,
				world.getMapx() * world.getIconSize().width ),
			inBoundBottom = Math.min( (eye.getY() + (int)Math.ceil( view.height / 2.0 ) + pixelY) * world.getIconSize().height,
				world.getMapy() * world.getIconSize().height );
		g.clearRect( outBoundLeft, outBoundTop,
			inBoundLeft - outBoundLeft, outBoundBottom - outBoundTop );
		g.clearRect( inBoundLeft, outBoundTop,
			outBoundRight - inBoundLeft, inBoundTop - outBoundTop );
		g.clearRect( inBoundRight, inBoundTop,
			outBoundRight - inBoundRight, outBoundBottom - inBoundTop );
		g.clearRect( inBoundLeft, inBoundBottom,
			inBoundRight - inBoundLeft, outBoundBottom - inBoundBottom );
		g.translate( inBoundLeft, inBoundTop );
	}
}
