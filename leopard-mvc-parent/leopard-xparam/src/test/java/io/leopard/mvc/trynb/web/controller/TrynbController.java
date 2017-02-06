package io.leopard.mvc.trynb.web.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.jetty.test.JettyTester;

@Controller
@RequestMapping("/")
public class TrynbController {

	@RequestMapping("/index.do")
	public ModelAndView index() {
		throw new RuntimeException("error1.");
	}

	@Test
	public void testIndex() throws Exception {
		// Server server = JettyServer.start("src/test/webapp");
		String result = JettyTester.doGet("http://localhost/index.do");
		System.out.println("result:" + result);
		Assert.assertEquals("error1.", result);
	}

	@RequestMapping("/welcome.do")
	public ModelAndView welcome() {
		throw new RuntimeException("error2.");
	}

	@Test
	public void testWelcome() throws Exception {
		String result = JettyTester.doGet("http://localhost/welcome.do");
		System.out.println("result:" + result);
		Assert.assertEquals("error2.", result);
	}

	@RequestMapping("/user.do")
	@ResponseBody
	public String user() {
		throw new RuntimeException("ok");
	}

	@Test
	public void testUser() throws Exception {
		String result = JettyTester.doGet("http://localhost/user.do");
		System.out.println("result:" + result);
		Assert.assertEquals("{\"status\":\"RuntimeException\",\"message\":\"ok\",\"data\":null}", result);
	}
}
