package io.leopard.web.freemarker;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public abstract class AbstractHttpServlet extends HttpServlet {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static final long serialVersionUID = 1L;

	public AbstractHttpServlet() {
		printServletInfo();
	}

	protected void printServletInfo() {
		Class<?> clazz = this.getClass();
		WebServlet servlet = clazz.getAnnotation(WebServlet.class);
		if (servlet == null) {
			return;
		}
		String name = servlet.name();
		String uri = StringUtils.arrayToDelimitedString(servlet.urlPatterns(), ",");
		System.err.println("WebServlet name:" + name + "\turi:" + uri + "\tclass:" + clazz.getName());
	}

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
