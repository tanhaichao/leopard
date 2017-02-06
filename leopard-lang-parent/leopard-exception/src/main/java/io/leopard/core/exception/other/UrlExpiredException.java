package io.leopard.core.exception.other;

/**
 * 链接已失效
 * 
 * @author 谭海潮
 *
 */
public class UrlExpiredException extends Exception {

	private static final long serialVersionUID = 1L;

	public UrlExpiredException(String message) {
		super("链接已失效[" + message + "]");
	}
}
