package io.leopard.web4j.admin;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.admin.Admin;

import org.junit.Test;

public class AdminTest {

	@Test
	public void Admin() {
		BeanAssert.assertModel(Admin.class);
	}

}