package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法枚举元素.
 * 
 * @author 谭海潮
 *
 */
public class EnumConstantInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public EnumConstantInvalidException(String message) {
		super(message);
	}

}
