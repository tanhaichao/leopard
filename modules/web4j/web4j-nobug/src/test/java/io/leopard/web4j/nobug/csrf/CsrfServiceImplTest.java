package io.leopard.web4j.nobug.csrf;

import io.leopard.test4j.mock.MockRequest;
import io.leopard.test4j.mock.MockResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.method.HandlerMethod;

public class CsrfServiceImplTest {

	protected CsrfServiceImpl csrfService = new CsrfServiceImpl();

	@Test
	public void makeToken() {
		csrfService.makeToken("hctan", 1234);
	}

	@Test
	public void setEnable() {
		CsrfServiceImpl.setEnable(false);
		Assert.assertFalse(csrfService.isEnable());
		CsrfServiceImpl.setEnable(true);
	}

	// @Test
	// public void checkToken() {
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	//
	// LoginHandler loginHandler = Mockito.mock(LoginHandler.class);
	//
	// CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());
	// LeopardMockito.setProperty(csrfService, loginHandler);
	//
	// csrfService.checkToken(request, response);
	//
	// Mockito.doNothing().when(csrfService).checkTokenByYyuid(request,
	// response, "token");
	// Mockito.doNothing().when(csrfService).checkTokenByUsername(request,
	// response, "token");
	// csrfService.checkToken(request, response, "token");
	//
	// Mockito.doReturn(true).when(loginHandler).isUseYyuid();
	// csrfService.checkToken(request, response, "token");
	// }

	@Test
	public void checkToken2() {

		MockResponse response = new MockResponse();

		CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());
		{
			MockRequest request = new MockRequest();
			request.addHeader("X-Real-IP", "127.0.0.1");
			csrfService.checkToken(request, response);
		}

		{
			MockRequest request = new MockRequest();
			request.addHeader("X-Real-IP", "192.168.1.1");
			csrfService.checkToken(request, response);
			request.addParameter("csrf-token", "null");
			csrfService.checkToken(request, response);
		}
		{
			MockRequest request = new MockRequest();
			request.addParameter("csrf-token", "token");
			csrfService.checkToken(request, response);

			Mockito.doNothing().when(csrfService).checkToken(request, response, "token");
			csrfService.checkToken(request, response);

			CsrfServiceImpl.setOnlyLog(false);
			Mockito.doNothing().when(csrfService).checkToken(request, response, "token");
			csrfService.checkToken(request, response);
		}
	}

	@Test
	public void CsrfServiceImpl() {

	}

	@Test
	public void isOfficeIp() {
		Assert.assertTrue(csrfService.isOfficeIp("127.0.0.1"));
		Assert.assertTrue(csrfService.isOfficeIp("172.17.0.74"));
		Assert.assertFalse(csrfService.isOfficeIp("192.168.1.1"));
	}

	// @Test
	// public void checkTokenByUsername() {
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	//
	// CsrfDao csrfDaoUsernameImpl = Mockito.mock(CsrfDao.class);
	//
	// CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());
	// LeopardMockito.setProperty(csrfService, csrfDaoUsernameImpl,
	// "csrfDaoUsernameImpl");
	// csrfService.checkTokenByUsername(request, response, "token");
	//
	// request.getSession().setAttribute("sessUsername", "hctan");
	// csrfService.checkTokenByUsername(request, response, "token");
	// }
	//
	// @Test
	// public void checkTokenByYyuid() {
	// MockRequest request = new MockRequest();
	// MockResponse response = new MockResponse();
	//
	// CsrfDao csrfDaoYyuidImpl = Mockito.mock(CsrfDao.class);
	//
	// CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());
	// LeopardMockito.setProperty(csrfService, csrfDaoYyuidImpl,
	// "csrfDaoYyuidImpl");
	// csrfService.checkTokenByYyuid(request, response, "token");
	//
	// request.getSession().setAttribute("sessYyuid", "1234");
	// csrfService.checkTokenByYyuid(request, response, "token");
	// }

	@Test
	public void checkByJsonView() {

		MockResponse response = new MockResponse();
		HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);

		CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());

		Mockito.doNothing().when(csrfService).checkToken(Mockito.any(HttpServletRequest.class), Mockito.any(HttpServletResponse.class));

		{
			MockRequest request = new MockRequest();
			request.setParameter("callback", "callback");
			csrfService.checkByJsonView(handlerMethod, request, response);
		}
		{
			MockRequest request = new MockRequest();
			request.setParameter("var", "varname");
			csrfService.checkByJsonView(handlerMethod, request, response);
		}
		{

			Mockito.doNothing().when(csrfService).checkAdminFolderReferer(Mockito.any(HandlerMethod.class), Mockito.any(HttpServletRequest.class));

			MockRequest request = new MockRequest();
			request.setParameter("csrf-token", "");
			csrfService.checkByJsonView(handlerMethod, request, response);

			request.setParameter("csrf-token", "null");
			csrfService.checkByJsonView(handlerMethod, request, response);
			request.setParameter("csrf-token", "token");
			csrfService.checkByJsonView(handlerMethod, request, response);
		}
	}

	@Test
	public void checkAdminFolderReferer() {
		HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);
		MockRequest request = new MockRequest();
		CsrfServiceImpl csrfService = Mockito.spy(new CsrfServiceImpl());

		request.setRequestURI("/admin/index.do");
		csrfService.checkAdminFolderReferer(handlerMethod, request);

		// Method method = Mockito.mock(Method.class);
		// Mockito.doReturn(method).when(handlerMethod).getMethod();
		//
		// Mockito.doReturn(LocationView.class).when(method).getReturnType();
		// csrfService.checkAdminFolderReferer(handlerMethod, request);

	}
}