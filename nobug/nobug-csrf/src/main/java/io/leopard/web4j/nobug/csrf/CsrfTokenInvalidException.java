package io.leopard.web4j.nobug.csrf;

public class CsrfTokenInvalidException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public CsrfTokenInvalidException(String message) {
		super(message);
	}

}
