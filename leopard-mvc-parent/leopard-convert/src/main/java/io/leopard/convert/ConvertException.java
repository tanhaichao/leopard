package io.leopard.convert;

/**
 * 对象转换异常
 * 
 * @author 谭海潮
 *
 */
public class ConvertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(String message, Throwable e) {
		super(message, e);
	}
}
