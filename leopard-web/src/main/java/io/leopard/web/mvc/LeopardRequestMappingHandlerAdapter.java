package io.leopard.web.mvc;

import java.util.List;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

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

}
