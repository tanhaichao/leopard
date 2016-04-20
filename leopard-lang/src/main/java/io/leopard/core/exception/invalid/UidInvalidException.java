package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法uid.
 * 
 * @author 阿海
 * 
 */
public class UidInvalidException extends InvalidException {
	private static final long serialVersionUID = 1L;

	public UidInvalidException(Long uid) {
		super("非法uid[" + uid + "].");
	}

}
