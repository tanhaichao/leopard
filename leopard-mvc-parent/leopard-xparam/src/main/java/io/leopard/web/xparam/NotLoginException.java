package io.leopard.web.xparam;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 未登录.
 * 
 * @author 阿海
 * 
 */
public class NotLoginException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public NotLoginException(String message) {
		super(message);
	}
}
