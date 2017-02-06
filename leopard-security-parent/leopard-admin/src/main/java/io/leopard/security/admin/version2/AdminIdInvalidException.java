package io.leopard.security.admin.version2;



import io.leopard.core.exception.InvalidException;

/**
 * 非法管理员ID.
 * 
 * @author 阿海
 *
 */
public class AdminIdInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public AdminIdInvalidException(long adminId) {
		super("非法管理员ID[" + adminId + "].");
	}

}