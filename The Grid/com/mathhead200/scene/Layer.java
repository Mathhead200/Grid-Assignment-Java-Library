package com.mathhead200.scene;

import java.awt.Color;
import java.awt.Point;


/**
 * A
 * @author Christopher D'Angelo
 */
public interface Layer
{
	/**
	 * This method is called for each point in the {@link Scene}
	 * @param point The coordinate that is being rendered
	 * @return The {@link java.awt.Color} that will be rendered at the given coordinate
	 */
	public Color draw(Point point);
}
