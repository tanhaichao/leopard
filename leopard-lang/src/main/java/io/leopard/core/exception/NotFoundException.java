package io.leopard.core.exception;

/**
 * 记录找不到(显示异常).
 * 
 * @author 阿海
 * 
 */
public class NotFoundException extends LeopardException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}
