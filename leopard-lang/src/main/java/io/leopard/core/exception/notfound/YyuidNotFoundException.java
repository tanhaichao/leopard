package io.leopard.core.exception.notfound;

import io.leopard.core.exception.NotFoundException;

/**
 * YYUID不存在.
 * 
 * @author 阿海
 * 
 */
public class YyuidNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public YyuidNotFoundException(long yyuid) {
		super("YY用户不存在[" + yyuid + "].");
	}

}
