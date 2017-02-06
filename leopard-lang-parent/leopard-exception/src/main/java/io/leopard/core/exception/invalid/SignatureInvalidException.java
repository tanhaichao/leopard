package io.leopard.core.exception.invalid;

import io.leopard.core.exception.InvalidException;

/**
 * 非法签名.
 * 
 * @author 阿海
 * 
 */
public class SignatureInvalidException extends InvalidException {

	private static final long serialVersionUID = 1L;

	public SignatureInvalidException(String message) {
		super(message);
	}

}
