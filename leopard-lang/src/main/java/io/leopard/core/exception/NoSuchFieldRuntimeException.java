package io.leopard.core.exception;

public class NoSuchFieldRuntimeException extends NotFoundRuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchFieldRuntimeException(String message) {
		super(message);
	}

	public NoSuchFieldRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
