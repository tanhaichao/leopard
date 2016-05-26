package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 错误的验证码.
 * 
 * @author 谭海潮
 *
 */
public class CaptchaWrongException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public CaptchaWrongException(String securityCode) {
		super("错误的验证码[" + securityCode + "].");
	}

}
