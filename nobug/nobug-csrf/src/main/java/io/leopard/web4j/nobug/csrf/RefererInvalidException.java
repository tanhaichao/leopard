package io.leopard.web4j.nobug.csrf;

/**
 * 来源信息不合法.
 * 
 * @author 阿海
 * 
 */
public class RefererInvalidException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public RefererInvalidException(String message) {
		super(message);
	}

}
