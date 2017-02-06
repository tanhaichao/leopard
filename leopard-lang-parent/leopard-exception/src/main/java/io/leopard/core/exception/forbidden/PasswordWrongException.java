package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

public class PasswordWrongException extends ForbiddenException {

	
	private static final long serialVersionUID = 1L;

	public PasswordWrongException(String message) {
		super(message);
	}

}
