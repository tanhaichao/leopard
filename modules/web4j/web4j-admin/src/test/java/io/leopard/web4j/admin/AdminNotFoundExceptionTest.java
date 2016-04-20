package io.leopard.web4j.admin;

import org.junit.Assert;
import org.junit.Test;

public class AdminNotFoundExceptionTest {

	@Test
	public void AdminNotFoundException() {
		Assert.assertEquals("msg", new AdminNotFoundException("msg").getMessage());
	}

}