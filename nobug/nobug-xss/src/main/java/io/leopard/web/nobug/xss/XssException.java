package io.leopard.web.nobug.xss;

public class XssException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public XssException(String message) {
		super(message);
	}

}
