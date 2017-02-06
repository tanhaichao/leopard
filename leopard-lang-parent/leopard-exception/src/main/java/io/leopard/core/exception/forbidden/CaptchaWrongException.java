package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 错误的验证码.
 * 
 * @author 谭海潮
 *
 */
public class CaptchaWrongException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public CaptchaWrongException(String message) {
		this(message, null);
	}

	public CaptchaWrongException(String message, String apiMessage) {
		super(message, apiMessage);
	}

}
