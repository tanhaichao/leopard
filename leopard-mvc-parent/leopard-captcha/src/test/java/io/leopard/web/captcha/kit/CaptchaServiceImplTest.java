package io.leopard.web.captcha.kit;

import org.junit.Test;

import io.leopard.core.exception.forbidden.CaptchaWrongException;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.JdbcFactory;
import io.leopard.web.captcha.FrequencyException;

public class CaptchaServiceImplTest {

	private CaptchaServiceImpl seccodeService = new CaptchaServiceImpl();

	public CaptchaServiceImplTest() {
		Jdbc jdbc = JdbcFactory.creaeJdbcMysqlImpl("112.126.75.27", "example", "example", "leopard");
		seccodeService.setJdbc(jdbc);
		seccodeService.setTableName("captcha");
		seccodeService.init();
	}

	@Test
	public void send() throws FrequencyException, CaptchaWrongException {
		String account = "13924718422";
		String seccode = seccodeService.send(account, CaptchaType.MOBILE, "test", "content");
		seccode = seccodeService.send(account, CaptchaType.MOBILE, "test", "content");
		Captcha bean = seccodeService.check(account, CaptchaType.MOBILE, "test", seccode);
		seccodeService.updateUsed(bean);
	}

}