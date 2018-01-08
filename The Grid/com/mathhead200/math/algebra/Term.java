package com.mathhead200.math.algebra;


public abstract class Term
{
	private String string;

	public Term(String string) {
		this.string = string;
	}

	public Term reduce() {
		return this;
	}

	public String toString() {
		return string;
	}


	/**
	 * Test main program
	 */
	public static void main(String[] args) {
		java.util.Random rand  = new java.util.Random();
		Term expression = new Number( (rand.nextInt(199) - 99) / 10.0 );
		for( int i = 0; i < 5; i++ ) {
			Number num = new Number( (rand.nextInt(199) - 99) / 10.0 );
			int c = rand.nextInt(13);
			boolean b = rand.nextBoolean();
			if( c < 4 )
				expression = b ? new Addition(expression, num) : new Addition(num, expression);
			else if( c < 8 )
				expression = b ? new Subtraction(expression, num) : new Subtraction(num, expression);
			else if( c < 10 )
				expression = b ? new Multiplication(expression, num) : new Multiplication(num, expression);
			else if( c < 12 )
				expression = b ? new Division(expression, num) : new Division(num, expression);
			else
				expression = b ? new Exponentiation(expression, num) : new Exponentiation(num, expression);
		}
		System.out.println(expression);
		System.out.println( expression.reduce() );
	}
}
