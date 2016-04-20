package io.leopard.web4j.parameter;

import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.test4j.mock.MockRequest;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LeopardMockRunner.class)
public class SessUidPageParameterTest {

	SessUidPageParameter page = new SessUidPageParameter();

	@Test
	public void getKey() {
		Assert.assertEquals("sessYyuid", page.getKey());
	}

	@Test
	public void getValue() {
		MockRequest request = new MockRequest();
		HttpSession session = request.getSession();
		{
			session.setAttribute("sessYyuid", "1234");
			Assert.assertEquals("1234", page.getValue(request));
		}
	}

	// @Test
	// public void getValue2() {
	// SessUidPageParameter page = Mockito.spy(new SessUidPageParameter());
	//
	// MockRequest request = new MockRequest();
	// HttpSession session = request.getSession();
	//
	// Mockito.doReturn(1L).when(page).getSessYyuid(session);
	// Assert.assertEquals("1", page.getValue(request, null));
	//
	// Mockito.doReturn(0L).when(page).getSessYyuid(session);
	// try {
	// page.getValue(request, null);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (NotLoginException e) {
	//
	// }
	//
	// }

	// @Test
	// public void getSessYyuid() {
	// LoginHandler loginHandler = Mockito.mock(LoginHandler.class);
	// SessUidPageParameter page = new SessUidPageParameter();
	// LeopardMockito.setProperty(page, loginHandler);
	//
	// Mockito.doReturn(true).when(loginHandler).isUseYyuid();
	// Assert.assertEquals(0, page.getSessYyuid(null));
	//
	// UclientFacadeService uclientFacadeService = Mockito.mock(UclientFacadeService.class);
	// PowerMockito.when(UclientFacadeModule.getUclientFacadeService()).thenReturn(uclientFacadeService);
	// Mockito.doReturn(false).when(loginHandler).isUseYyuid();
	//
	// HttpSession session = Mockito.mock(HttpSession.class);
	// Mockito.doReturn("hctan").when(session).getAttribute("sessUsername");
	// Mockito.doReturn(1L).when(uclientFacadeService).getYyuid("hctan");
	// Assert.assertEquals(1, page.getSessYyuid(session));
	//
	// }

}