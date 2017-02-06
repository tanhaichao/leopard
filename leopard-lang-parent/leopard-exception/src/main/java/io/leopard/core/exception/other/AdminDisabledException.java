package io.leopard.core.exception.other;

import io.leopard.core.exception.ForbiddenException;

/**
 * 管理员账号已被禁用
 * 
 * @author 谭海潮
 *
 */
public class AdminDisabledException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public AdminDisabledException(String username) {
		super("管理员账号[" + username + "]已被禁用");
	}

}
