package io.leopard.web4j.trynb.resolver;

import io.leopard.core.exception.StatusCodeException;
import io.leopard.web4j.trynb.model.TrynbInfo;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class WsViewTrynbResolver implements TrynbResolver {

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception, TrynbInfo trynbInfo) {
		WsView webserviceView = new WsView(null);
		if (exception instanceof StatusCodeException) {
			StatusCodeException e = (StatusCodeException) exception;
			webserviceView.setStatus(e.getStatus());
			webserviceView.setMessage(e.getMessage());
		}
		else {
			webserviceView.setStatus(trynbInfo.getStatusCode());
			webserviceView.setMessage(trynbInfo.getMessage());
		}
		return webserviceView;
	}

}
