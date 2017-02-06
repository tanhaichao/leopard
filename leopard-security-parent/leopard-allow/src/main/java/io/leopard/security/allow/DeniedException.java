package io.leopard.security.allow;

public class DeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeniedException(String message) {
		super(message);
	}
}
