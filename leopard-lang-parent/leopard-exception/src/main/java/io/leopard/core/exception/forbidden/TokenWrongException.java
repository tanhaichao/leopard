package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 错误的Token.
 * 
 * @author 谭海潮
 *
 */
public class TokenWrongException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public TokenWrongException(String message) {
		super(message);
	}

}
