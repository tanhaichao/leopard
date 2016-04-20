package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

public class PasswordInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public PasswordInvalidException(String message) {
		super(message);
	}

}
