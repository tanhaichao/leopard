package io.leopard.core.exception;

/**
 * 顶层RuntimeException
 * 
 * @author 阿海
 * 
 */
public class LeopardRuntimeException extends RuntimeException {

	protected static final long serialVersionUID = 1L;

	public LeopardRuntimeException(String message) {
		super(message);
	}

	public LeopardRuntimeException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	public LeopardRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
