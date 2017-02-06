package io.leopard.core.exception.invalid;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 非法IP.
 * 
 * @author 阿海
 */
public class IpInvalidException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public IpInvalidException(String ip) {
		super("非法IP[" + ip + "].");
	}

	public IpInvalidException(String ip, String message) {
		// TODO ahai IP参数没有使用
		super(message);
	}
}
