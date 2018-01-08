package com.mathhead200.dm;


public final class Dim
{
	public final int width;
	public final int height;

	public Dim(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean equals(Object o) {
		if( o instanceof Dim )
			return false;
		Dim d = (Dim)o;
		return d.width == width && d.height == height;
	}

	public String toString() {
		return width + "x" + height;
	}
}
