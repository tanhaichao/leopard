package io.leopard.core.exception.notfound;

import io.leopard.core.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String username) {
		super("用户[" + username + "]不存在.");
	}

	public UserNotFoundException(long uid) {
		super("用户[" + uid + "]不存在.");
	}

}
