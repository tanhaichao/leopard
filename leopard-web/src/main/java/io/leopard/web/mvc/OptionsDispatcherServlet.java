package io.leopard.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.leopard.web.mvc.resource.ResourcesDispatcherServlet;

public class OptionsDispatcherServlet extends ResourcesDispatcherServlet {

	private static final long serialVersionUID = 1L;

	public void setDispatchOptionsRequest(boolean dispatchOptionsRequest) {
		logger.info("dispatchOptionsRequest:" + dispatchOptionsRequest);
		super.setDispatchOptionsRequest(dispatchOptionsRequest);
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (true) {
			super.doOptions(request, response);
			return;
		}
		logger.info("doOptions:" + request.getRequestURI());

		// header('Access-Control-Allow-Headers:x-requested-with,content-type');

		response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.addHeader("Access-Control-Allow-Origin", "*");
		// super.doOptions(request, response);
		return;
	}
}
