package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

/**
 * 枚举类型权限异常
 * 
 * @author 谭海潮
 *
 */
public class EnumConstantForbiddenException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public EnumConstantForbiddenException(String message) {
		super(message);
	}

}
