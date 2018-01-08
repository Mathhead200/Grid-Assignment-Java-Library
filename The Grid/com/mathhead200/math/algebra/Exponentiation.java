package com.mathhead200.math.algebra;


public class Exponentiation extends BinaryOperation
{
	public Exponentiation(Term a, Term b) {
		super("^", a, b );
	}

	protected double operation(double numA, double numB) {
		return Math.pow(numA, numB);
	}
}
