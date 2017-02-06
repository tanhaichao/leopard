package io.leopard.core.exception.forbidden;

import io.leopard.core.exception.ForbiddenException;

public class IpForbiddenException extends ForbiddenException {
	

	private static final long serialVersionUID = 1L;

	public IpForbiddenException(String ip) {
		super("IP[" + ip + "]没有访问权限.");
	}

	public IpForbiddenException(String ip, String message) {
		// TODO ahai ip参数没有使用
		super(message);
	}
}
