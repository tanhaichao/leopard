package io.leopard.web4j.trynb.resolver;

import io.leopard.web4j.trynb.model.TrynbInfo;
import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.OkTextView;
import io.leopard.web4j.view.WsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

public class TrynbResolverImpl implements TrynbResolver {

	private TrynbResolver jsonViewTrynbResolver = new JsonViewTrynbResolver();
	private TrynbResolver wsViewTrynbResolver = new WsViewTrynbResolver();
	private TrynbResolver okTextViewTrynbResolver = new OkTextViewTrynbResolver();

	@Override
	public ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception, TrynbInfo trynbInfo) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class<?> returnType = handlerMethod.getMethod().getReturnType();

		ModelAndView view = null;
		if (JsonView.class.isAssignableFrom(returnType)) {
			view = this.jsonViewTrynbResolver.resolveView(request, response, handlerMethod, exception, trynbInfo);
		}
		else if (returnType.equals(WsView.class)) {
			view = this.wsViewTrynbResolver.resolveView(request, response, handlerMethod, exception, trynbInfo);
		}
		else if (returnType.equals(OkTextView.class)) {
			view = this.okTextViewTrynbResolver.resolveView(request, response, handlerMethod, exception, trynbInfo);
		}
		return view;
	}

}
