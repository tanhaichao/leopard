package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardRuntimeException;

public class OtherException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public OtherException(String message) {
		super(message);
	}

	public OtherException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
