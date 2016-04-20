package io.leopard.core.exception;

public class ClassNotFoundRuntimeException extends NotFoundRuntimeException {
	private static final long serialVersionUID = 1L;

	public ClassNotFoundRuntimeException(String message) {
		super(message);
	}

	public ClassNotFoundRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
