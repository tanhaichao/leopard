package io.leopard.security.admin.menu;

import io.leopard.core.exception.ForbiddenException;

/**
 * 没有角色权限.
 * 
 * @author 谭海潮
 *
 */
public class RoleForbiddenException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public RoleForbiddenException(String message) {
		super(message);
	}

}
