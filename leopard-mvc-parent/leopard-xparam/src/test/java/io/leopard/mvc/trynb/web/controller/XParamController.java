package io.leopard.mvc.trynb.web.controller;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;
import io.leopard.web.xparam.None;

@Controller
@RequestMapping("/xparam/")
public class XParamController {

	@RequestMapping("/welcome.do")
	public long welcome(Long sessUid) {
		return sessUid;
	}

	@Test
	public void testWelcome() throws Exception {
		JettyServer.start("src/test/webapp");
		String result = Httpnb.doGet("http://localhost/xparam/welcome.do");
		System.out.println("result:" + result);
		// Assert.assertEquals("error1.", result);
	}

	@RequestMapping("/paging.do")
	@ResponseBody
	public String paging(long start, @None("20") int size) {
		return "start:" + start + " size:" + size;
	}

	@Test
	public void testPaging() throws Exception {
		JettyServer.start("src/test/webapp");
		String result = Httpnb.doGet("http://localhost/xparam/paging.do");
		System.out.println("result:" + result);
		// Assert.assertEquals("error1.", result);
	}
}
