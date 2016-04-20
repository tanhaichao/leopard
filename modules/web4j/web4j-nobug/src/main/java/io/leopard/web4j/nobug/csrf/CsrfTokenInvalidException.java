package io.leopard.web4j.nobug.csrf;

import io.leopard.core.exception.InvalidException;

public class CsrfTokenInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public CsrfTokenInvalidException(String message) {
		super(message);
	}

}
