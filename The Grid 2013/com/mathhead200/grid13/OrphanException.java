package com.mathhead200.grid13;


/**
 * Thrown when an method needing a {@link Parent}
 * 	is invoked on a parentless {@link GridItem}.
 *
 * @author Christopher D'Angelo
 * @version Mar 13, 2013
 */
@SuppressWarnings("serial")
public class OrphanException extends GridException
{
	public OrphanException() {
		super();
	}

	public OrphanException(String message) {
		super(message);
	}

	public OrphanException(String message, Throwable cause) {
		super(message, cause);
	}

	public OrphanException(Throwable cause) {
		super(cause);
	}
}
