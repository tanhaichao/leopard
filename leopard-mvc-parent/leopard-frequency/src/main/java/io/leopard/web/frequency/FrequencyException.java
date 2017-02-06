package io.leopard.web.frequency;

/**
 * 访问太频繁.
 * 
 * @author ahai
 * 
 */
public class FrequencyException extends Exception {
	

	private static final long serialVersionUID = 1L;

	public FrequencyException(Object passport, String uri) {
		super("您[" + passport + "]访问[" + uri + "]太频繁了，歇一会儿吧.");
	}

}
