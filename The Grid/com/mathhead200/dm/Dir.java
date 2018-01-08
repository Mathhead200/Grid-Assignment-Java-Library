package com.mathhead200.dm;


public final class Dir
{
	public static final byte
		NORTH = 1,
		SOUTH = 2,
		EAST = 4,
		WEST = 8,
		NORTHEAST = NORTH | EAST,
		NORTHWEST = NORTH | WEST,
		SOUTHEAST = SOUTH | EAST,
		SOUTHWEST = SOUTH | WEST;

	private static boolean isDir(int dir, int cardinal) {
		return (dir & cardinal) != 0;
	}

	public static boolean isNorth(int dir) {
		return isDir(dir, NORTH);
	}

	public static boolean isSouth(int dir) {
		return isDir(dir, SOUTH);
	}

	public static boolean isEast(int dir) {
		return isDir(dir, EAST);
	}

	public static boolean isWest(int dir) {
		return isDir(dir, WEST);
	}
}
