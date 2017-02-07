package io.leopard.myjetty.workbench;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

public class WebappServletTest {

	@Test
	public void compile() throws ServletException, IOException {
		WebappServlet servlet = new WebappServlet();
		servlet.compile(null, null);
	}

}