package io.leopard.core.exception;

public class TimeoutRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TimeoutRuntimeException(String msg) {
		super(msg);
	}

	public TimeoutRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
