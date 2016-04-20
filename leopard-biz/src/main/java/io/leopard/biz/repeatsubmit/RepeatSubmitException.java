package io.leopard.biz.repeatsubmit;

/**
 * 重复提交.
 * 
 * @author 阿海
 * 
 */
public class RepeatSubmitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RepeatSubmitException(String message) {
		super(message);
	}

}
