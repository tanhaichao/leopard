package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法时间格式.
 * 
 * @author 阿海
 */
public class DateTimeInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public DateTimeInvalidException(String datetime) {
		super("非法时间格式[" + datetime + "].");
	}
}
