package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 已登录.
 * 
 * @author 阿海
 * 
 */
public class LoginedException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginedException(String message) {
		super(message);
	}
}
