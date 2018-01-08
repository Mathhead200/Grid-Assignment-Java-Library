package com.mathhead200.math;


public class CMath
{
	public static int add(int a, int b) {
		int m = a ^ b,
		n = a & b;
		if( n != 0 )
			return add(m, n << 1);
		else
			return m;
	}

	public static int subtract(int a, int b) {
		int m = a ^ b,
		n = ~a & b;
		if( n != 0 )
			return subtract(m, n << 1);
		else
			return m;
	}

	public static int multiply(int a, int b) {
		int sum = 0;
		for( int i = 0; i < 32; i++ ) {
			int p = 1 << i;
			if( (b & p) == p )
				sum = add(sum, a << i);
		}
		return sum;
	}


	public static long add(long a, long b) {
		long m = a ^ b,
		n = a & b;
		if( n != 0 )
			return add(m, n << 1);
		else
			return m;
	}

	public static long subtract(long a, long b) {
		long m = a ^ b,
		n = ~a & b;
		if( n != 0 )
			return subtract(m, n << 1);
		else
			return m;
	}

	public static long multiply(long a, long b) {
		long sum = 0;
		for( int i = 0; i < 64; i++ ) {
			long p = 1 << i;
			if( (b & p) == p )
				sum = add(sum, a << i);
		}
		return sum;
	}

	public static long GCD(long a, long b) {
		//Euclidean Algorithm
		if( Math.abs(a) < Math.abs(b) )
			return GCD(b, a);

		long q = a / b;
		if( a < 0 ) //fix so r >= 0
			q += b < 0 ? 1 : -1;
		long r = a - b * q;

		//System.out.println( String.format( "%d = (%d)%d + %d", a, b, q, r ) );
		if( r == 0 )
			return b;
		else
			return GCD(b, r);
	}

	public static int GCD(int a, int b) {
		//explicitly cast int a,b to long to avoid infinite recursion
		return (int)GCD( (long)a, (long)b );
	}

	public static boolean isPrime(long n) {
		if( n <= 1 )
			return false;
		int sqrt = (int)Math.sqrt(n);
		for( int i = 2; i < sqrt; i++ )
			if( n % i == 0 )
				return false;
		return true;
	}

	public static long nextPrime(long p) {
		do {
			p++;
		} while( !isPrime(p) );
		return p;
	}

	public static long getPrime(long n) {
		long p = 2;
		while( n > 0 ) {
			p = nextPrime(p);
			n--;
		}
		return p;
	}

	public static long[] factor(long n) {
		java.util.ArrayList<Long> factors = new java.util.ArrayList<Long>();
		long p = 2;
		factors.add(0l);
		while( n > 1 ) {
			long r = n % p;
			if( r == 0 ) {
				n /= p;
				factors.set( factors.size() - 1, factors.get(factors.size() - 1) + 1 );
			} else {
				p = nextPrime(p);
				factors.add(0l);
			}
		}
		long[] f = new long[factors.size()];
		for( int i = 0; i < f.length; i++ )
			f[i] = factors.get(i);
		return f;
	}

	public static long eulerPhi(long n) {
		long ans = n;
		long[] exponents = factor(n);
		java.util.ArrayList<Long> fac = new java.util.ArrayList<Long>();
		for( int p = 0; p < exponents.length; p++ )
			if( exponents[p] > 0 )
				fac.add( getPrime(p) );
		System.out.println( "prime factors: " + fac );
		for( int step = 0; step < fac.size(); step++ ) {
			int[] i = new int[step + 1];
			for( int _ = 0; _ < i.length; _++ )
				i[_] = i.length - 1 - _;
			java.util.ArrayList<Long> products = new java.util.ArrayList<Long>();
			while( i[0] < fac.size() ) {
				products.add(1l);
				for( int _ = 0; _ < i.length; _++ )
					products.set( products.size() - 1, products.get(products.size() - 1) * fac.get(i[_]) );
				for( int _ = 0; _ < i.length; _++ ) {
					i[_]++;
					if( i[_] < fac.size() )
						break;
					if( _ < i.length - 1 )
						i[_] = i[_ + 1] + 2;
				}
			}
			System.out.println( "products of " + (step + 1) + " factor(s): " + products );
			if( step % 2 == 0 )
				for( int a = 0; a < products.size(); a++ )
					ans -= n / products.get(a);
			else
				for( int a = 0; a < products.size(); a++ )
					ans += n / products.get(a);
		}
		return ans;
	}

	public static int eulerPhi(int n) {
		return (int)eulerPhi( (long)n );
	}
}
