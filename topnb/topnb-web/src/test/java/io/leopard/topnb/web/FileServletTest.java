package io.leopard.topnb.web;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.test.JettyTester;

import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;

public class FileServletTest {

	@Test
	public void isValidFilename() {
		Assert.assertTrue(FileServlet.isValidFilename("test.jpg"));
		Assert.assertTrue(FileServlet.isValidFilename("img/test.jpg"));
		Assert.assertTrue(FileServlet.isValidFilename("img/icons/monitor/ic_ok.png"));
		Assert.assertFalse(FileServlet.isValidFilename("../img/test.jpg"));
	}

	@Test
	public void test() throws Exception {
		Server server = JettyTester.start();
		{
			String result = Httpnb.doGet("http://localhost/topnb/file.leo?f=css/bootstrap.css");
			// System.out.println("result:" + result);
		}
		{
			String result = Httpnb.doGet("http://localhost/topnb/file.leo?f=test.css");
			System.out.println("result:" + result);
			Assert.assertEquals("test", result);
		}
		server.stop();
	}

	// @Test
	// public void read() throws IOException {
	// byte[] bytes = FileServlet.read("js/plugins/jquery/jquery-ui-1.10.1.custom.min.js");
	// String str = new String(bytes);
	// System.out.println(str.substring(str.length() - 100));
	// }

}