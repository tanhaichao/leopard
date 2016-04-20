package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法YY号.
 * 
 * @author 阿海
 * 
 */
public class ImidInvalidException extends InvalidException {
	private static final long serialVersionUID = 1L;

	public ImidInvalidException(Long yyuid) {
		super("非法YY号[" + yyuid + "].");
	}

}
