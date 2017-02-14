package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 用户已被禁用.
 * 
 * @author 谭海潮
 *
 */
public class UserDisabledException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public UserDisabledException(String message) {
		super(message);
	}
}
