package io.leopard.web4j.nobug.xss;

import io.leopard.core.exception.InvalidException;

public class XssException extends InvalidException {


	private static final long serialVersionUID = 1L;

	public XssException(String message) {
		super(message);
	}

}
