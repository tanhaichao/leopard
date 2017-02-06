package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 外部接口异常
 * 
 * @author 阿海
 */
public class OutSideException extends LeopardRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutSideException(String message) {
		super(message);
	}

	public OutSideException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
