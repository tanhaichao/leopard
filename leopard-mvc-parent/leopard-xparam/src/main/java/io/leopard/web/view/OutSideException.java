package io.leopard.web.view;

/**
 * 外部接口异常
 * 
 * @author 阿海
 */
public class OutSideException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutSideException(String message) {
		super(message);
	}

	public OutSideException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
