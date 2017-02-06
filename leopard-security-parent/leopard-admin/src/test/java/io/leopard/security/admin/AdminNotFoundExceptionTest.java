package io.leopard.security.admin;

import io.leopard.security.admin.AdminNotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class AdminNotFoundExceptionTest {

	@Test
	public void AdminNotFoundException() {
		Assert.assertEquals("msg", new AdminNotFoundException("msg").getMessage());
	}

}