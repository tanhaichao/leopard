package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistException;

/**
 * 手机号码已存在.
 * 
 * @author 谭海潮
 *
 */
public class MobileExistException extends ExistException {

	private static final long serialVersionUID = 1L;

	public MobileExistException(String mobile) {
		super("手机号码[" + mobile + "]已存在.");
	}

}
