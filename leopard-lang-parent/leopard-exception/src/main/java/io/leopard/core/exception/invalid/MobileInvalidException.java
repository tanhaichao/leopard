package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法手机号.
 * 
 * @author 谭海潮
 *
 */
public class MobileInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public MobileInvalidException(String mobile) {
		super("非法手机号[" + mobile + "].");
	}

}
