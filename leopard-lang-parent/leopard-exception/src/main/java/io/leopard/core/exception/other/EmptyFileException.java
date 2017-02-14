package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardException;

/**
 * 文件为空
 * 
 * @author 谭海潮
 *
 */
public class EmptyFileException extends LeopardException {

	private static final long serialVersionUID = 1L;

	public EmptyFileException(String message) {
		super(message);
	}
}
