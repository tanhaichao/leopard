package io.leopard.core.exception;

/**
 * 没有权限.
 * 
 * @author 阿海
 * 
 */
public class ForbiddenException extends LeopardException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message);
	}

	@Override
	public String getDesc() {
		return "没有权限.";
	}
}
