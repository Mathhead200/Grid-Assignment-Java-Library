package com.mathhead200.math.algebra;


public class Division extends BinaryOperation
{
	public Division(Term a, Term b) {
		super("/", a, b);
	}

	protected double operation(double numA, double numB) {
		return numA / numB;
	}
}
