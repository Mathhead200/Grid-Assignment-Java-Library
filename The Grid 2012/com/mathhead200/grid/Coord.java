package com.mathhead200.grid;


public class Coord
{
	public final int x;
	public final int y;
	public final int z;

	public Coord(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coord(int x, int y) {
		this(x, y, 0);
	}

	public boolean equals(Object obj) {
		if( !(obj instanceof Coord) )
			return false;
		Coord c = (Coord)obj;
		return x == c.x && y == c.y && c.z == z;
	}

	public int hashCode() {
		//zzzz yyyy yyyy yyyy yyxx xxxx xxxx xxxx
		return (x & 0x3FFF) & ((y & 0x3FFF) << 14) & (z << 28);
	}
}
