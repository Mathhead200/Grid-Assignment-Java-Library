package com.mathhead200.math.algebra;


public class Addition extends BinaryOperation
{
	public Addition(Term a, Term b) {
		super(" + ", a, b);
	}

	protected double operation(double numA, double numB) {
		return numA + numB;
	}

	/*
	public Term reduce() {
		Term a = operands[0],
			b = operands[1];
		if( a instanceof Variable && a.equals(b) ) {
			Variable var = (Variable)a;
			return new Multiplication( new Number(2), a );
		} else
			return super.reduce();
	}
	*/
}
