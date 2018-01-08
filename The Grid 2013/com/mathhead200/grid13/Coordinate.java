package com.mathhead200.grid13;


public final class Coordinate
{
	public static final Coordinate ORIGIN = new Coordinate(0, 0);

	public final int x;
	public final int y;

	private final int hashCode;


	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;

		hashCode = (x << 16) | (y & 0x0000FFFF);
	}


	public boolean equals(Object that) {
		if( this == that )
			return true;
		if( that instanceof Coordinate ) {
			Coordinate a = (Coordinate)that;
			return x == a.x && y == a.y;
		} else
			return false;
	}

	public int hashCode() {
		return hashCode;
	}

	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
}
