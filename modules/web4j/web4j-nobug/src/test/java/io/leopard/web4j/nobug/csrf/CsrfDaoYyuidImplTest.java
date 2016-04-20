package io.leopard.web4j.nobug.csrf;

import io.leopard.web4j.nobug.csrf.CsrfTokenInvalidException;

import org.junit.Assert;
import org.junit.Test;

public class CsrfDaoYyuidImplTest {

	private final CsrfServiceImpl csrfService = new CsrfServiceImpl();

	@Test
	public void checkToken() {
		CsrfDaoYyuidImpl csrfDao = new CsrfDaoYyuidImpl();

		try {
			csrfDao.checkToken("1234", "token");
			Assert.fail("为什么没有抛异常?");
		}
		catch (CsrfTokenInvalidException e) {

		}
		{
			String token = csrfService.makeToken("hctan", 1234L, 1);
			csrfDao.checkToken("1234", token);
		}

		try {
			String token = "a-b-1";
			csrfDao.checkToken("1234", token);
			Assert.fail("为什么没有抛异常?");
		}
		catch (CsrfTokenInvalidException e) {

		}
	}

	@Test
	public void CsrfDaoYyuidImpl() {

	}

}