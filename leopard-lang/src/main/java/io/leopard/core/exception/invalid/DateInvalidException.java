package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法日期格式.
 * 
 * @author 阿海
 */
public class DateInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public DateInvalidException(String date) {
		super("非法日期格式[" + date + "].");
	}
}
