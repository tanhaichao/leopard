package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 接口不允许调用
 * 
 * @author 谭海潮
 *
 */
public class ApiForbiddenException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public ApiForbiddenException(String message) {
		super(message);
	}

}
