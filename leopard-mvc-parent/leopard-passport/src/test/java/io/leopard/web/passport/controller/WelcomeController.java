package io.leopard.web.passport.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;

@Controller
public class WelcomeController {

	@RequestMapping("/welcome.do")
	@ResponseBody
	public long welcome(Long sessUid) {
		return sessUid;
	}

	@RequestMapping("/welcome.do")
	@ResponseBody
	public long welcome2(Long sessShopId) {
		return sessShopId;
	}

	@Test
	public void testWelcome() throws Exception {
		JettyServer.start("src/test/webapp");
		{
			String str = Httpnb.doGet("http://localhost/passport/login.leo?uid=1");
			System.out.println("str:" + str);
		}
		String result = Httpnb.doGet("http://localhost/welcome.do");
		System.out.println("result:" + result);
		Assert.assertEquals("{\"status\":\"RuntimeException\",\"message\":\"ok\",\"data\":null}", result);
	}

	public static void main(String[] args) throws Exception {
		JettyServer.start("src/test/webapp");
	}
}
