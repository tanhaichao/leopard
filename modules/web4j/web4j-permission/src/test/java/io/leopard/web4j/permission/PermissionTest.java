package io.leopard.web4j.permission;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.permission.Permission;

import org.junit.Test;

public class PermissionTest {

	@Test
	public void Permission() {
		BeanAssert.assertModel(Permission.class);
	}

}