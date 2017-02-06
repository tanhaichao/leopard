package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistException;

public class UserExistException extends ExistException {

	private static final long serialVersionUID = 1L;

	public UserExistException(String username) {
		super("用户[" + username + "]已存在.");
	}
}
