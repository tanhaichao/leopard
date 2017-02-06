package io.leopard.web.view;

import io.leopard.core.exception.LeopardRuntimeException;

/**
 * 状态码异常.
 * 
 * @author ahai
 * 
 */
public class StatusCodeException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	private String status;

	private String msg;

	public StatusCodeException(String status, String message, String msg) {
		super(message + "[" + status + "]");
		this.msg = msg;
		this.status = status;
	}

	public String getMsg() {
		return this.msg;
	}

	public String getStatus() {
		return status;
	}

}
