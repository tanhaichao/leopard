package io.leopard.schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MemdbBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class<?> getBeanClass(Element element) {
		return DataSourceManager.getMemdbRsyncImpl();
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		int maxSize = Integer.parseInt(element.getAttribute("maxSize"));
		String redisRef = element.getAttribute("redis-ref");
		String channel = element.getAttribute("channel");
		builder.addConstructorArgValue(maxSize);

		builder.addPropertyReference("redis", redisRef);
		builder.addPropertyValue("channel", channel);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");
	}
}