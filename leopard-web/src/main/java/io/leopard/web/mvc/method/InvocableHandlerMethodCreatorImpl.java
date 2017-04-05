package io.leopard.web.mvc.method;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

public class InvocableHandlerMethodCreatorImpl implements InvocableHandlerMethodCreator {

	private InvocableHandlerMethodCreator creator = null;

	public InvocableHandlerMethodCreatorImpl() {
		Iterator<InvocableHandlerMethodCreator> iterator = ServiceLoader.load(InvocableHandlerMethodCreator.class).iterator();
		if (iterator.hasNext()) {
			creator = iterator.next();
		}
	}

	@Override
	public ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
		ServletInvocableHandlerMethod invocableHandlerMethod;
		if (creator != null) {
			invocableHandlerMethod = creator.createInvocableHandlerMethod(handlerMethod);
			if (invocableHandlerMethod != null) {
				return invocableHandlerMethod;
			}
		}
		return new ServletInvocableHandlerMethod(handlerMethod);
	}

}
