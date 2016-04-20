package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data4j.schema.RegisterComponentUtil;

public class MemcacheBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String type = element.getAttribute("type");
		if ("redis".equalsIgnoreCase(type)) {
			return this.createRedisImpl(element, parserContext);
		}
		// else if ("memcached".equalsIgnoreCase(type)) {
		// return this.createMemcachedImpl(element, parserContext);
		// }
		else {
			throw new IllegalArgumentException("未知类型[" + type + "].");
		}

	}

	protected BeanDefinition createRedisImpl(Element element, ParserContext parserContext) {
		String id = element.getAttribute("id");
		String server = element.getAttribute("server");

		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String timeout = element.getAttribute("timeout");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getMemcacheRedisImpl());

		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}

		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}

		builder.addPropertyValue("server", server);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

	// protected BeanDefinition createMemcachedImpl(Element element, ParserContext parserContext) {
	// String id = element.getAttribute("id");
	// String server = element.getAttribute("server");
	//
	// String maxActive = element.getAttribute("maxActive");
	//
	// String sanitizeKeys = element.getAttribute("sanitizeKeys");
	//
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceClassUtil.getMemcacheImpl());
	//
	// if (StringUtils.isNotEmpty(maxActive)) {
	// builder.addPropertyValue("maxConn", Integer.valueOf(maxActive));
	// }
	//
	// if (StringUtils.isNotEmpty(sanitizeKeys)) {
	// builder.addPropertyValue("sanitizeKeys", sanitizeKeys);
	// }
	//
	// builder.addPropertyValue("server", server);
	//
	// builder.setScope(BeanDefinition.SCOPE_SINGLETON);
	// builder.setLazyInit(false);
	// builder.setInitMethodName("init");
	// builder.setDestroyMethodName("destroy");
	//
	// return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	// }
}