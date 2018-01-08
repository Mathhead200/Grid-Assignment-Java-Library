package com.mathhead200.grid13;


/**
 * The superclass for all (new) exceptions thrown from classes in this package.
 *
 * @author Christopher D'Angelo
 * @version Feb 25, 2013
 */
@SuppressWarnings("serial")
public class GridException extends RuntimeException
{
	public GridException() {
		super();
	}

	public GridException(String message) {
		super(message);
	}

	public GridException(String message, Throwable cause) {
		super(message, cause);
	}

	public GridException(Throwable cause) {
		super(cause);
	}
}
