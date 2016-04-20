package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法服务器ID.
 * 
 * @author 阿海
 * 
 */
public class ServerIdInvalidException extends InvalidException {
	private static final long serialVersionUID = 1L;

	public ServerIdInvalidException(String serverId) {
		super("非法服务器ID[" + serverId + "].");
	}

}
