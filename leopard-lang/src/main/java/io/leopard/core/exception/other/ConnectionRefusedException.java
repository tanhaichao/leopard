package io.leopard.core.exception.other;

import java.io.IOException;

/**
 * 连接服务器出错
 * 
 * @author 阿海
 * 
 */
public class ConnectionRefusedException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionRefusedException(String message) {
		super(message);
	}

}
