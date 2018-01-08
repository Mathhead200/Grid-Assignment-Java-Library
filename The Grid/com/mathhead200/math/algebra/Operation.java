package com.mathhead200.math.algebra;


public abstract class Operation extends Term implements Cloneable
{
	protected Term[] operands;

	public Operation(String string, Term...operands) {
		super("(" + string + ")");
		this.operands = operands;
	}

	public Term reduce() {
		Term[] reducedOperands = new Term[operands.length];
		for( int i = 0; i < reducedOperands.length; i++ )
			reducedOperands[i] = operands[i].reduce();
		for( int i = 0; i < operands.length; i++ )
			if( !reducedOperands[i].equals( operands[i] ) )
				try {
					Operation operation = (Operation)clone();
					operation.operands = reducedOperands;
					return operation.reduce();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					break;
				}
		return this;
	}
}
