package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法邮箱.
 * 
 * @author 谭海潮
 *
 */
public class EmailInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public EmailInvalidException(String email) {
		super("非法邮箱[" + email + "].");
	}

}
