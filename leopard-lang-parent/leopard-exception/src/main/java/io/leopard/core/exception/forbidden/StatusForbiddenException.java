package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 状态权限异常.
 * 
 * @author 谭海潮
 *
 */
public class StatusForbiddenException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public StatusForbiddenException(String message) {
		super(message);
	}

}
