package com.mathhead200.grid13;


/**
 * Thrown when an attempt is made to add a {@link #GridItem} to an already occupied location.
 *
 * @author Christopher D'Angelo
 * @version Feb 25, 2013
 */
@SuppressWarnings("serial")
public class OccupiedException extends GridException
{
	public OccupiedException() {
		super();
	}

	public OccupiedException(String message) {
		super(message);
	}

	public OccupiedException(String message, Throwable cause) {
		super(message, cause);
	}

	public OccupiedException(Throwable cause) {
		super(cause);
	}
}
