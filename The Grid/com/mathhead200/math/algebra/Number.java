package com.mathhead200.math.algebra;


public class Number extends Term
{
	public final double value;

	public Number(double value) {
		super("[" + value + "]");
		this.value = value;
	}
}
