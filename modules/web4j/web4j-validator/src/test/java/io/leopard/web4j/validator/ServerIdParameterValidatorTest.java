package io.leopard.web4j.validator;

import io.leopard.core.exception.invalid.ServerIdInvalidException;
import io.leopard.web4j.validator.ServerIdParameterValidator;

import org.junit.Assert;
import org.junit.Test;

public class ServerIdParameterValidatorTest {

	protected ServerIdParameterValidator validator = new ServerIdParameterValidator();

	@Test
	public void getKey() {
		Assert.assertEquals("serverId", validator.getKey());
	}

	@Test
	public void check() {
		validator.check("s1");
		try {
			validator.check("");
			Assert.fail("怎么没有抛异常?");
		}
		catch (ServerIdInvalidException e) {

		}
	}

}