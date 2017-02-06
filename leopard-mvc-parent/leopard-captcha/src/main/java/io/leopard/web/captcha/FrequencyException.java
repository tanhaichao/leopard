package io.leopard.web.captcha;

/**
 * 访问太频繁.
 * 
 * @author 谭海潮
 * 
 */
public class FrequencyException extends Exception {

	private static final long serialVersionUID = 1L;

	public FrequencyException(String message) {
		super(message);
	}

}
