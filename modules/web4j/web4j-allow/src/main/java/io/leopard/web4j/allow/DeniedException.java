package io.leopard.web4j.allow;

public class DeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DeniedException(String message) {
		super(message);
	}
}
