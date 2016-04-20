package io.leopard.web4j.frequency;

/**
 * 访问太频繁.
 * 
 * @author ahai
 * 
 */
public class FrequencyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FrequencyException(String user, String uri) {
		super("您[" + user + "]访问[" + uri + "]太频繁了，歇一会儿吧.");
	}

}
