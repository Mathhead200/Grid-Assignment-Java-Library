package com.mathhead200.grid13;


/**
 * Thrown when an attempt is made to add a {@link #GridItem}
 * 	to multiple parents, or at multiple locations on the same grid.
 *
 * @author Christopher D'Angelo
 * @version Feb 25, 2013
 */
@SuppressWarnings("serial")
public class BilocationException extends GridException
{
	public BilocationException() {
		super();
	}

	public BilocationException(String message) {
		super(message);
	}

	public BilocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BilocationException(Throwable cause) {
		super(cause);
	}
}
