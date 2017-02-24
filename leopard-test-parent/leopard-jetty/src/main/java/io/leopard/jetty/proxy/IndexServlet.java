package io.leopard.jetty.proxy;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("request:" + request.getClass().getName());
		System.out.println("response:" + response.getClass().getName());

		output(response, "Hello Leopard Proxy");
	}

	protected void output(HttpServletResponse response, String str) throws IOException {
		byte[] bytes = str.getBytes();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentLength(bytes.length);

		System.out.println("response:" + response.getClass().getName());

		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
	}
}
