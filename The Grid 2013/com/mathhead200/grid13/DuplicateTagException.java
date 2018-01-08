package com.mathhead200.grid13;


/**
 * Thrown when an attempt is made to add a {@link #GridItem} labeled with a tag
 * 	to a grid where another item is using that same tag already.
 *
 * @author Christopher D'Angelo
 * @version Feb 25, 2013
 */
@SuppressWarnings("serial")
public class DuplicateTagException extends GridException
{
	public DuplicateTagException() {
		super();
	}

	public DuplicateTagException(String message) {
		super(message);
	}

	public DuplicateTagException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateTagException(Throwable cause) {
		super(cause);
	}
}
