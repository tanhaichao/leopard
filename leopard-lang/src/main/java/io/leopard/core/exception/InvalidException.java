package io.leopard.core.exception;

/**
 * 非法参数.
 * 
 * @author 阿海
 */
public class InvalidException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidException(String message) {
		super(message);
	}
}
