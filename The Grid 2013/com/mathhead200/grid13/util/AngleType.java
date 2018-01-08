package com.mathhead200.grid13.util;


public final class AngleType
{
	public static AngleType REVOLUTION = new AngleType(1, "rev");
	public static AngleType DEGREE = new AngleType(360, "deg");
	public static AngleType RADIAN = new AngleType(2 * Math.PI, "rad");
	public static AngleType GRADIANS = new AngleType(400, "grad");

	public final double inCircle;
	public final String symbol;

	private AngleType(double inCircle, String symbol) {
		this.inCircle = inCircle;
		this.symbol = symbol;
	}

	public static double convert(double angle, AngleType from, AngleType to) {
		return angle * to.inCircle / from.inCircle;
	}
}
