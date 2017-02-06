package io.leopard.jdbc;

import io.leopard.jdbc.InvalidParamDataAccessException;

import org.junit.Assert;
import org.junit.Test;

public class InvalidParamDataAccessExceptionTest {

	@Test
	public void InvalidParamDataAccessException() {
		Assert.assertEquals("msg", new InvalidParamDataAccessException("msg").getMessage());
		Assert.assertEquals("msg; nested exception is java.lang.Exception: msg", new InvalidParamDataAccessException(new Exception("msg")).getMessage());
	}

}