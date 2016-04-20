package io.leopard.web4j.validator;

import io.leopard.core.exception.invalid.UsernameInvalidException;
import io.leopard.web4j.validator.UsernameParameterValidator;

import org.junit.Assert;
import org.junit.Test;

public class UsernameParameterValidatorTest {

	protected UsernameParameterValidator validator = new UsernameParameterValidator();

	@Test
	public void getKey() {
		Assert.assertEquals("username", validator.getKey());
	}

	@Test
	public void check() {
		validator.check("hctan");
		try {
			validator.check("");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UsernameInvalidException e) {

		}
	}

}