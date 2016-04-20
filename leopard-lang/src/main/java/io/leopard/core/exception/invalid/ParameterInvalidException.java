package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法参数.
 * 
 * @author 阿海
 * 
 */
public class ParameterInvalidException extends InvalidException {
	private static final long serialVersionUID = 1L;

	public ParameterInvalidException(String message) {
		super(message);
	}

}
