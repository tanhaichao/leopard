package io.leopard.core.exception.invalid;

public class PassportInvalidException extends UsernameInvalidException {

	private static final long serialVersionUID = 1L;

	public PassportInvalidException(String passport) {
		super(passport);
	}

	public PassportInvalidException(String passport, String message) {
		super(passport, message);
	}
}
