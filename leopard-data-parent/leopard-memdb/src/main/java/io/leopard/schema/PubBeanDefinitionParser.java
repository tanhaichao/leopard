package io.leopard.schema;

import io.leopard.data4j.pubsub.PubSubRsyncImpl;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class PubBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class<?> getBeanClass(Element element) {
		return PubSubRsyncImpl.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String id = element.getAttribute("id");
		String subRef = element.getAttribute("sub-ref");
		String server = element.getAttribute("server");
		String listenOneSelf = element.getAttribute("listenOneSelf");

		String channel = "pub:" + id;

		builder.addPropertyReference("sub", subRef);
		builder.addPropertyValue("server", server);
		builder.addPropertyValue("channel", channel);
		builder.addPropertyValue("listenOneSelf", listenOneSelf);

		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");
	}
}