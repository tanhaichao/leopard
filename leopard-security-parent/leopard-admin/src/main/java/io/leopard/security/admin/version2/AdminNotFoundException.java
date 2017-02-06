package io.leopard.security.admin.version2;

import io.leopard.core.exception.NotFoundException;

/**
 * 管理员不存在.
 * 
 * @author 阿海
 *
 */
public class AdminNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public AdminNotFoundException(long adminId) {
		super("管理员[" + adminId + "]不存在.");
	}

	public AdminNotFoundException(String username) {
		super("管理员[" + username + "]不存在.");
	}
}