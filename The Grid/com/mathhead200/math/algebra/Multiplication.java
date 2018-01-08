package com.mathhead200.math.algebra;


public class Multiplication extends BinaryOperation
{
	public Multiplication(Term a, Term b) {
		super("*", a, b );
	}

	protected double operation(double numA, double numB) {
		return numA * numB;
	}
}
