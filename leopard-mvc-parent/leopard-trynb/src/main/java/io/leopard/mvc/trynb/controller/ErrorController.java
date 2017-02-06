package io.leopard.mvc.trynb.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
	private final Log logger = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/error.do")
	public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getAttribute("javax.servlet.error.exception");
		Throwable exception = (Throwable) obj;
		logger.error(exception.getMessage(), exception);

		// String uri = request.getRequestURI();
		// String clientInfo = ErrorUtil.getClientInfo(request, uri, exception.getMessage());
		// logger.error(clientInfo, exception);

		response.setContentType("text/html; charset=utf-8");
		// response.setStatus(200);
		Writer out = response.getWriter();
		out.write("error msg:" + exception.getMessage());
		return null;
	}

}
