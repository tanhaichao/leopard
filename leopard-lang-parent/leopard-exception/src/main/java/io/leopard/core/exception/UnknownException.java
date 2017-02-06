package io.leopard.core.exception;

/**
 * 未知异常
 * 
 * @author yuanwen
 * 
 */
public class UnknownException extends LeopardRuntimeException {

	private static final long serialVersionUID = -3616397576450593769L;

	public UnknownException() {
		super("未知异常.");
	}

	public UnknownException(String message) {
		super(message);
	}

}