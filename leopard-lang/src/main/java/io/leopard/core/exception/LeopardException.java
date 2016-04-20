package io.leopard.core.exception;

/**
 * 顶层显示异常类
 * 
 * @author 阿海
 * 
 */
public class LeopardException extends Exception {

	// private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeopardException() {
		super();
	}

	public LeopardException(String message) {
		super(message);
	}

	public LeopardException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * 该接口用于生成文档.
	 * 
	 * @return
	 */
	public String getDesc() {
		return null;
	}

	// public void setMessage(String message) {
	// this.message = message;
	// }
	//
	// public String getMessage() {
	// if (message == null || message.length() == 0) {
	// return super.getMessage();
	// }
	// else {
	// return this.message;
	// }
	// }
}
