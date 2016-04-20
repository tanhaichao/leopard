package io.leopard.web4j.admin;

import io.leopard.core.exception.NotFoundRuntimeException;

public class AdminNotFoundException extends NotFoundRuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminNotFoundException(String message) {
		super(message);
	}

}
