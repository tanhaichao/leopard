package io.leopard.json;

public class JsonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JsonException(String message) {
		super(message);
	}

	public JsonException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public JsonException(String message, Throwable cause) {
		super(message, cause);
	}
}