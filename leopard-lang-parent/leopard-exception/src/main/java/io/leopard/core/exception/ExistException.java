package io.leopard.core.exception;

/**
 * 记录已存在异常.
 * 
 * @author 阿海
 * 
 */
public class ExistException extends LeopardException {

	private static final long serialVersionUID = 1L;

	public ExistException(String message) {
		super(message);
	}
}
