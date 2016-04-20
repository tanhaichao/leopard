package io.leopard.web4j.proxy;

import io.leopard.test4j.mock.MockRequest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ResinProxyTest {

	@Test
	public void ResinProxy() {
		new ResinProxy();
	}

	@Test
	public void getResinIp() {
		MockRequest request = new MockRequest();
		request.setCookies(new Cookie("resinIp", "127.0.0.1"));
		Assert.assertEquals("127.0.0.1", ResinProxy.getResinIp(request));

		request.setParameter("resinIp", "127.0.0.2");
		Assert.assertEquals("127.0.0.2", ResinProxy.getResinIp(request));
	}

	@Test
	public void getUrl() {
		MockRequest request = new MockRequest();
		request.setServerName("leopard.yy.com");
		request.addParameter("name1", "value1");
		request.addParameter("name2", "value2");
		String url = ResinProxy.getUrl("127.0.0.2", "/index.do", request);
		Assert.assertEquals("http://127.0.0.2:8081/index.do?name1=value1&name2=value2", url);
		System.out.println("url:" + url);
	}

	@Test
	public void outputError() throws IOException {
		ServletResponse res = Mockito.mock(ServletResponse.class);
		PrintWriter out = Mockito.mock(PrintWriter.class);
		Mockito.doReturn(out).when(res).getWriter();

		// Writer out = res.getWriter();
		ResinProxy.outputError(res, "message");
	}

}