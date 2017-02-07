package io.leopard.topnb.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractHttpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.doService(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
			output(response, e.getMessage());
		}
	}

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	protected void output(HttpServletResponse response, String str) throws IOException {
		byte[] bytes = str.getBytes();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentLength(bytes.length);

		// response.setDateHeader("Expires", System.currentTimeMillis() + 1000 * 3600 * 24);
		// Flush byte array to servlet output stream.
		OutputStream out = response.getOutputStream();
		out.write(bytes);
		out.flush();
	}
}
