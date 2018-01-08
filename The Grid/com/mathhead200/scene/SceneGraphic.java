package com.mathhead200.scene;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;


/**
 * @author Christopher D'Angelo
 */
public abstract class SceneGraphic
{
	/**
	 * The shape in which this graphic will be drawn
	 */
	protected Shape boundary;

	/**
	 * This method is called for each point that lies within
	 * 	or on the boundaries of this graphic
	 * @param point The coordinate that is being rendered
	 * @return The {@link java.awt.Color} that will be rendered at the given coordinate
	 */
	abstract public Color draw(Point point);
}
