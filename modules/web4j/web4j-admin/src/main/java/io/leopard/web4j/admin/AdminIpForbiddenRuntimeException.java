package io.leopard.web4j.admin;

import io.leopard.core.exception.ForbiddenRuntimeException;

/**
 * 您所在的位置IP不允许登陆后台系统.
 * 
 * @author 阿海
 * 
 */
public class AdminIpForbiddenRuntimeException extends ForbiddenRuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminIpForbiddenRuntimeException(String ip) {
		super("您所在的位置IP不允许登陆后台系统!" + "[ip=" + ip + "]");
	}

}
