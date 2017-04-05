package io.leopard.web.mvc;

import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

public class LeopardRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

	@Override
	public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		logger.info("setArgumentResolvers:" + argumentResolvers);
		super.setArgumentResolvers(argumentResolvers);
	}

	@Override
	public void setCustomArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		logger.info("setCustomArgumentResolvers:" + argumentResolvers);
		super.setCustomArgumentResolvers(argumentResolvers);
	}

	@Override
	protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
		return new LeopardServletInvocableHandlerMethod(handlerMethod);
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		logger.info("getArgumentResolvers():" + getArgumentResolvers());
	}
}
