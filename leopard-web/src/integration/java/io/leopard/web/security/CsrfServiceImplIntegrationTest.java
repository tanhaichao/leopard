package io.leopard.web.security;

import org.junit.Test;

public class CsrfServiceImplIntegrationTest {

	private final CsrfServiceImpl csrfService = new CsrfServiceImpl();
	private final CsrfDao csrfDaoYyuidImpl = new CsrfDaoYyuidImpl();
	private final CsrfDao csrfDaoUsernameImpl = new CsrfDaoUsernameImpl();

	@Test
	public void makeToken() {
		String sessUsername = "hctan";
		long sessYyuid = 1;
		String token = csrfService.makeToken(sessUsername, sessYyuid);
		csrfDaoUsernameImpl.checkToken(sessUsername, token);
		csrfDaoYyuidImpl.checkToken(sessYyuid + "", token);
	}

}