package io.leopard.security.admin;

import io.leopard.security.admin.AdminIpForbiddenException;

import org.junit.Assert;
import org.junit.Test;

public class AdminIpForbiddenRuntimeExceptionTest {

	@Test
	public void AdminIpForbiddenRuntimeException() {
		Assert.assertEquals("您所在的位置IP不允许登陆后台系统![ip=127.0.0.1]", new AdminIpForbiddenException("127.0.0.1").getMessage());
	}

}