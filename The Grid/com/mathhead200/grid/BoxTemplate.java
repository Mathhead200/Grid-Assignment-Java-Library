package com.mathhead200.grid;

import java.awt.Color;
import javax.swing.border.Border;


/**
 * holds the fields for the Box constructor,
 * use {@link BoxTemplate#makeBox()} to create a Box in this state
 * @author Christopher D'Angelo
 * @author JBD Computers &trade;
 * @version 1.0
 */
public class BoxTemplate
{
	public int width = 32;
	public int height = 32;
	public Color t = Box.DEFAULT_TURF;
	public Border border = Box.DEFAULT_BORDER;

	public BoxTemplate(int width, int height, Color t, Border border) {
		this.width = width;
		this.height = height;
		this.t = t;
		this.border = border;
	}

	public BoxTemplate(int width, int height, Color t) {
		this.width = width;
		this.height = height;
		this.t = t;
	}

	public BoxTemplate(int size, Color t) {
		this(size, size, t);
	}

	public BoxTemplate(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public BoxTemplate(int size) {
		this(size, size);
	}

	public BoxTemplate(Color t) {
		this.t = t;
	}

	public BoxTemplate() {}


	public Box makeBox() {
		return new Box(width, height, t, border);
	}
}
