package io.leopard.security.admin;

import io.leopard.security.admin.Admin;
import io.leopard.test.mock.BeanAssert;

import org.junit.Test;

public class AdminTest {

	@Test
	public void Admin() {
		BeanAssert.assertModel(Admin.class);
	}

}