package io.leopard.web.view.trynb;

import io.leopard.mvc.trynb.model.TrynbInfo;
import io.leopard.mvc.trynb.resolver.TrynbResolver;
import io.leopard.web.view.StatusCodeException;
import io.leopard.web.view.WsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

public class WsViewTrynbResolver implements TrynbResolver {

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception exception, TrynbInfo trynbInfo) {
		Class<?> returnType = handler.getMethod().getReturnType();
		if (!returnType.equals(WsView.class)) {
			return null;
		}

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
