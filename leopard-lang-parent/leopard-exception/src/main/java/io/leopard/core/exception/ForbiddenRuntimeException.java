package io.leopard.core.exception;

/**
 * 没有权限.
 * 
 * @author 阿海
 * 
 */
public class ForbiddenRuntimeException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public ForbiddenRuntimeException(String message) {
		super(message);
	}
}
