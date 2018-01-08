package com.mathhead200.scene;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Scene extends Canvas
{
	private static class DefaultLayer implements Layer {
		public Color draw(Point point) {
			return Color.BLACK;
		}
	}

	public Shape lens;
	public List<Layer> layers = new ArrayList<Layer>(1);
	public List<SceneGraphic> elements = new ArrayList<SceneGraphic>();

	/**
	 * Creates a scene with the given viewing area
	 * @param lens The initial viewing area of the scene
	 */
	public Scene(Shape lens) {
		this.lens = lens;
		setBackground( new Color(255, 255, 255, 0) );
		layers.add( new DefaultLayer() );
		Rectangle2D bounds = lens.getBounds2D();
		setSize( (int)Math.ceil(bounds.getHeight()), (int)Math.ceil(bounds.getWidth()) );
	}

	/**
	 * Creates a scene of the given size
	 * @param width
	 * @param height
	 */
	public Scene(int width, int height) {
		this( new Rectangle(width, height) );
	}

	public void paint(Graphics g) {
		if( !(g instanceof Graphics2D) )
			return;
		Graphics2D g2d = (Graphics2D)g;
		Rectangle2D bounds = lens.getBounds2D();
		//drawing layers
		for( Layer layer : layers )
			for( int x = (int)Math.floor(bounds.getMinX()); x < bounds.getMaxX(); x++ )
				for( int y = (int)Math.floor(bounds.getMinY()); y < bounds.getMaxY(); y++ ) {
					Point point = new Point(x, y);
					if( lens.contains(point) ) {
						g2d.setColor( layer.draw(point) );
						g2d.drawLine( point.x, point.y, point.x, point.y );
					}
				}
		for( SceneGraphic ele : elements )
			for( int x = (int)Math.floor(bounds.getMinX()); x < bounds.getMaxX(); x++ )
				for( int y = (int)Math.floor(bounds.getMinY()); y < bounds.getMaxY(); y++ ) {
					Point point = new Point(x, y);
					if( lens.contains(point) && ele.boundary.contains(point) ) {
						g2d.setColor( ele.draw(point) );
						g2d.drawLine( point.x, point.y, point.x, point.y );
					}
				}
	}
}
