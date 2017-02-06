package io.leopard.web.captcha;

import io.leopard.core.exception.InvalidException;

/**
 * 非法验证码.
 * 
 * @author 谭海潮
 *
 */
public class CaptchaInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public CaptchaInvalidException(String message) {
		super(message);
	}

	public CaptchaInvalidException(String message, String apiMessage) {
		super(message, apiMessage);
	}

}
