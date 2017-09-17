package io.leopard.web.mvc;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import io.leopard.data.factory.LeopardDefaultListableBeanFactory;

public class LeopardXmlWebApplicationContext extends XmlWebApplicationContext {

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		DefaultListableBeanFactory beanFactory = new LeopardDefaultListableBeanFactory(getInternalParentBeanFactory());
		// logger.info("createBeanFactory:" + beanFactory.getClass().getName());
		return beanFactory;
	}

}
