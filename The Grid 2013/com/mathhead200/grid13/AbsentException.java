package com.mathhead200.grid13;


@SuppressWarnings("serial")
public class AbsentException extends GridException
{
	public AbsentException() {
		super();
	}

	public AbsentException(String message) {
		super(message);
	}

	public AbsentException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbsentException(Throwable cause) {
		super(cause);
	}
}
