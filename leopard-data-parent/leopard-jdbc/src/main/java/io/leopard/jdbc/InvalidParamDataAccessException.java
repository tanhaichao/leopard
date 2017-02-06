package io.leopard.jdbc;

import org.springframework.dao.DataAccessException;

public class InvalidParamDataAccessException extends DataAccessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParamDataAccessException(String msg) {
		super(msg);
	}

	public InvalidParamDataAccessException(Throwable e) {
		super(e.getMessage(), e);
		System.out.println("e:" + e.getMessage() + " " + super.getMessage());
	}
}
