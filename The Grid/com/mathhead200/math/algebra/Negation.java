package com.mathhead200.math.algebra;


public class Negation extends Operation
{
	public Negation(Term var) {
		super( "-" + var, var );
	}
}
