package io.leopard.mvc.trynb.web.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class XParamRequestMappingHandlerAdapter implements InitializingBean, BeanFactoryAware {

	
	@Override
	public void afterPropertiesSet() {
		System.out.println("XParamRequestMappingHandlerAdapter afterPropertiesSet");
		// super.afterPropertiesSet();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("XParamRequestMappingHandlerAdapter setBeanFactory:" + beanFactory);

	}
}
