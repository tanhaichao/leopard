package io.leopard.web.mvc.option;

import io.leopard.core.exception.NotFoundException;

/**
 * 
 * @author 谭海潮
 *
 */
public class OptionNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public OptionNotFoundException(String message) {
		super(message);
	}

}
