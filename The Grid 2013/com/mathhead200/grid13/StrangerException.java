package com.mathhead200.grid13;


/**
 * Thrown when an attempt is made to (re)move an item from a grid that it isn't located on.
 *
 * @author Christopher D'Angelo
 * @version Feb 25, 2013
 */
@SuppressWarnings("serial")
public class StrangerException extends GridException
{
	public StrangerException() {
		super();
	}

	public StrangerException(String message) {
		super(message);
	}

	public StrangerException(String message, Throwable cause) {
		super(message, cause);
	}

	public StrangerException(Throwable cause) {
		super(cause);
	}
}
