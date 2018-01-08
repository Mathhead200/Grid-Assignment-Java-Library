package com.mathhead200.math.algebra;


public class Subtraction extends BinaryOperation
{
	public Subtraction(Term a, Term b) {
		super("-", a, b );
	}

	protected double operation(double numA, double numB) {
		return numA - numB;
	}

}
