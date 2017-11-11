package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistException;

/**
 * 邮箱已存在.
 * 
 * @author 谭海潮
 *
 */
public class EmailExistException extends ExistException {

	private static final long serialVersionUID = 1L;

	public EmailExistException(String email) {
		super("邮箱[" + email + "]已存在.");
	}

}
