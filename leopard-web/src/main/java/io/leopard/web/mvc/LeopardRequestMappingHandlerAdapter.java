package io.leopard.web.mvc;

import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import io.leopard.web.mvc.method.InvocableHandlerMethodCreator;
import io.leopard.web.mvc.method.InvocableHandlerMethodCreatorImpl;

public class LeopardRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

	private InvocableHandlerMethodCreator invocableHandlerMethodCreator = new InvocableHandlerMethodCreatorImpl();

	@Override
	public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// logger.info("setArgumentResolvers:" + argumentResolvers);
		super.setArgumentResolvers(argumentResolvers);
	}

	@Override
	public void setCustomArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// logger.info("setCustomArgumentResolvers:" + argumentResolvers);
		super.setCustomArgumentResolvers(argumentResolvers);
	}

	@Override
	protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
		return invocableHandlerMethodCreator.createInvocableHandlerMethod(handlerMethod);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		// logger.info("getArgumentResolvers():" + getArgumentResolvers());
	}
}
