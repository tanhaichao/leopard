package io.leopard.web.mvc;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

public class LeopardServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

	public LeopardServletInvocableHandlerMethod(HandlerMethod handlerMethod) {
		super(handlerMethod);
	}

	@Override
	protected Object doInvoke(Object... args) throws Exception {

		return super.doInvoke(args);
	}

}
