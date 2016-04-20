package io.leopard.core.exception;


/**
 * 访问太频繁.
 * 
 * @author ahai
 * 
 */
public class ConnectionLimitException extends LeopardRuntimeException {

	private static final long serialVersionUID = 1L;

	public ConnectionLimitException(String user, String uri) {
		super("您[" + user + "]访问[" + uri + "]太频繁了，歇一会儿吧.");
	}

}
