package io.leopard.security.admin;

/**
 * 您所在的位置IP不允许登陆后台系统.
 * 
 * @author 阿海
 * 
 */
public class AdminIpForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminIpForbiddenException(String ip) {
		super("您所在的位置IP不允许登陆后台系统!" + "[ip=" + ip + "]");
	}

}
