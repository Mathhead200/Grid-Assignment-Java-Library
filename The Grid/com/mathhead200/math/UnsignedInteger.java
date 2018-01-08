package com.mathhead200.math;


@SuppressWarnings("serial")
public final class UnsignedInteger extends Number implements Comparable<UnsignedInteger>
{
	public static final UnsignedInteger MAX_VALUE = new UnsignedInteger(0xffffffff);
	public static final UnsignedInteger MIN_VALUE = new UnsignedInteger(0x00000000);

	//private static byte flags = 0x00;

	private final int value;

	/**
	 * If <code>value</code> is greater then or equal to <code>0</code>
	 * then this unsigned integer's value will be the same.<br>
	 * However, if <code>value</code> is less <code>0</code>
	 * then this unsigned integer's value will be <code>value + 2<sup>32</sup></code>.
	 * @param value - The internal bit pattern for this unsigned integer.
	 */
	public UnsignedInteger(int value) {
		this.value = value;
	}

	/*
	public UnsignedInteger(String s) {
		value = 0;
	}
	*/

	public boolean equals(Object o) {
		try {
			return value == ((UnsignedInteger)o).value;
		} catch(ClassCastException e) {
			return false;
		}
	}

	public double doubleValue() {
		return 0;
	}

	public float floatValue() {
		return 0;
	}

	/**
	 * @return The internal bit pattern of this unsigned integer.
	 */
	public int intValue() {
		return value;
	}

	/**
	 * @return The value of this unsigned integer as a <code>long</code>.
	 */
	public long longValue() {
		return value >= 0 ? (long)value : (long)value + 0xffffffffl;
	}

	public int compareTo(UnsignedInteger n) {
		int a = value,
		    b = n.value;
		while( b != 0 ) {
			int aBit = a >> 7,
			    bBit = b >> 7;
			if( aBit != bBit )
				return aBit - bBit;
			a <<= 1;
			b <<= 1;
		}
		if( a != b )
			return 1;
		return 0;
	}


	/**
	 * Compare a to b, treating them as unsigned integers.
	 * @param a
	 * @param b
	 * @return a negative integer, zero, or a positive integer,
	 *         if <code>a</code> is less then, equal to, or greater then <code>b</code> (respectively)
	 */
	public static int compare(int a, int b) {
		return new UnsignedInteger(a).compareTo( new UnsignedInteger(b) );
	}


	/**
	 * @param n
	 * @return (this + n)
	 */
	public UnsignedInteger add(UnsignedInteger n) {
		return new UnsignedInteger(value + n.value);
	}

	/**
	 * @param n
	 * @return (this - n)
	 */
	public UnsignedInteger subtract(UnsignedInteger n) {
		return new UnsignedInteger(value - n.value);
	}

	/**
	 * @param n
	 * @return (this * n);
	 */
	public UnsignedInteger multiply(UnsignedInteger n) {
		int ans = 0;
		for( int i = 0; i < Integer.SIZE; i++ )
			if( (n.value & (0x00000001 << i)) != 0 )
				ans += value << i;
		return new UnsignedInteger(ans);
	}

	public UnsignedInteger divide(UnsignedInteger n) {
		if( value == 0 )
			return new UnsignedInteger(0);
		if( n.value == 0 )
			throw new ArithmeticException("divide by zero");
		int ans = 0;
		int nValue = n.value;
		for( int i = Integer.SIZE - 1; i >= 0; i--)
			if( compare(value, (nValue >> i)) >= 0 ) {
				ans += 1 << i;
				nValue -= value << i;
				if( compare(nValue, value) < 0 )
					break;
			}
		return new UnsignedInteger(nValue); //???
	}
}
