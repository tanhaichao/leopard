package io.leopard.web4j.parameter;

import org.junit.Assert;
import org.junit.Test;

public class SessUsernamePageParameterTest {
	SessUsernamePageParameter page = new SessUsernamePageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("sessUsername", page.getKey());
	}

	// @Test
	// public void getValue() {
	// MockRequest request = new MockRequest();
	// HttpSession session = request.getSession();
	// {
	// session.setAttribute("sessUsername", "hctan");
	// Assert.assertEquals("hctan", page.getValue(request, null));
	// }
	//
	// LoginHandler loginHandler = Mockito.mock(LoginHandler.class);
	// LeopardMockito.setProperty(page, loginHandler);
	//
	// {
	// session.removeAttribute("sessUsername");
	// Mockito.doReturn(false).when(loginHandler).isUseYyuid();
	// try {
	// page.getValue(request, null);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotLoginException e) {
	//
	// }
	// // NotLoginException
	// }
	// {
	// session.removeAttribute("sessUsername");
	// Mockito.doReturn(true).when(loginHandler).isUseYyuid();
	// try {
	// page.getValue(request, null);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotLoginException e) {
	//
	// }
	// // NotLoginException
	// }
	// }

}