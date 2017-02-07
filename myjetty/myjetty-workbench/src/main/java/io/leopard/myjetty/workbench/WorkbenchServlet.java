package io.leopard.myjetty.workbench;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.webapp.WebAppContext;

import io.leopard.myjetty.webapp.WebappServiceImpl;

public class WorkbenchServlet extends HttpServlet {

	
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		System.out.println("doGet request:" + request);

		try {
			this.handler(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("error:" + e.getMessage());
		}

	}

	protected void handler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		String code = request.getParameter("code");
		// AssertUtil.assertNotEmpty(code, "code is empty.");

		String body;
		if ("status".equals(action)) {
			body = this.status(code);
		}
		else if ("start".equals(action)) {
			body = this.start(code);
		}
		else if ("stop".equals(action)) {
			body = this.stop(code);
		}
		else {
			throw new IllegalArgumentException("未知action[" + action + "].");
		}
		response.getWriter().println(body);
	}

	protected String status(String code) {
		WebAppContext webapp = WebappServiceImpl.getInstance().getWebapp(code);
		if (webapp == null) {
			return "stopped";
		}
		if (webapp.isStarted()) {
			return "started";
		}
		else if (webapp.isStopped()) {
			return "stopped";
		}
		else if (webapp.isStarting()) {
			return "starting";
		}
		else if (webapp.isStopping()) {
			return "stopping";
		}
		throw new RuntimeException("未知状态");
	}

	protected String start(String code) throws Exception {
		WebappServiceImpl.getInstance().start(code);
		return "started";
	}

	protected String stop(String code) throws Exception {
		WebappServiceImpl.getInstance().stop(code);
		return "stopped";
	}
}
