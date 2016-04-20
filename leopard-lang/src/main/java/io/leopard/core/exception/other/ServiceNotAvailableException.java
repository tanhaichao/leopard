package io.leopard.core.exception.other;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 服务不可用（系统维护中)
 * 
 * @author 阿海
 */
public class ServiceNotAvailableException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceNotAvailableException(String message) {
		super(message);
	}

}
