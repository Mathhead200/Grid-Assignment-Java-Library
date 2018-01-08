package com.mathhead200.math.algebra;


public abstract class BinaryOperation extends Operation
{
	public BinaryOperation(String symbol, Term a, Term b) {
		super( a + symbol + b, a, b );
	}

	abstract protected double operation(double numA, double numB);

	public Term reduce() {
		Term a = operands[0],
			b = operands[1];
		if( a instanceof Number && b instanceof Number ) {
			Number numA = (Number)a,
				numB = (Number)b;
			return new Number( operation(numA.value, numB.value) );
		} else
			return super.reduce();
	}
}
