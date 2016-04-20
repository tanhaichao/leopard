package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

public class UserForbiddenException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public UserForbiddenException(String message) {
		super(message);
	}

}
