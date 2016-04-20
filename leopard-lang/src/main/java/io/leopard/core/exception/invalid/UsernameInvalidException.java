package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

public class UsernameInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public UsernameInvalidException(String username) {
		super("非法用户名[" + username + "].");
	}

	public UsernameInvalidException(String username, String message) {
		// TODO ahai username参数没有使用
		super(message);
	}
}
