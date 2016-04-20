package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class RedisBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class<?> getBeanClass(Element element) {
		return DataSourceManager.getRedisImpl();
	}

	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		// private String server;
		// private int maxActive;
		// private boolean enableBackup;
		// private int timeout = 3000;
		String server = element.getAttribute("server");
		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String enableBackup = element.getAttribute("enableBackup");
		String backupTime = element.getAttribute("backupTime");
		String timeout = element.getAttribute("timeout");

		if (StringUtils.isNotEmpty(server)) {
			builder.addPropertyValue("server", server);
		}
		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}

		if (StringUtils.isNotEmpty(enableBackup)) {
			builder.addPropertyValue("enableBackup", Boolean.valueOf(enableBackup));
		}

		builder.addPropertyValue("backupTime", backupTime);
		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}

		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

	}
}