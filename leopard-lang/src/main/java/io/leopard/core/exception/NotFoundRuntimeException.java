package io.leopard.core.exception;

/**
 * 记录找不到(隐式异常).
 * 
 * @author 阿海
 * 
 */
public class NotFoundRuntimeException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundRuntimeException(String message) {
		super(message);
	}

	public NotFoundRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
