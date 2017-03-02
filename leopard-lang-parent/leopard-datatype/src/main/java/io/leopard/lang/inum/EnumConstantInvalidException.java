package io.leopard.lang.inum;

/**
 * 非法枚举元素.
 * 
 * @author 谭海潮
 *
 */
public class EnumConstantInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnumConstantInvalidException(String message) {
		super(message);
	}

}
