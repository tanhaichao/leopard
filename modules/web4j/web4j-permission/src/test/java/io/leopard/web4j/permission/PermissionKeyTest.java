package io.leopard.web4j.permission;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.permission.PermissionKey;

import org.junit.Test;

public class PermissionKeyTest {

	@Test
	public void PermissionKey() {
		BeanAssert.assertModel(PermissionKey.class);
	}

}