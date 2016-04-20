package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data4j.schema.RegisterComponentUtil;

/**
 * Redis数据源标签
 * 
 * @author 阿海
 * 
 */
public class MemcacheDsnBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(MemcacheDsnBeanDefinitionParser.class, parserContext);
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");

		String maxActive = element.getAttribute("maxActive");
		String initialPoolSize = element.getAttribute("initialPoolSize");
		String timeout = element.getAttribute("timeout");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getMemcacheRedisImpl());

		String server = this.getServer(name);
		builder.addPropertyValue("server", server);

		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(initialPoolSize)) {
			builder.addPropertyValue("initialPoolSize", Integer.valueOf(initialPoolSize));
		}

		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

	protected String getServer(String name) {
		// active1.redis.host=172.17.1.236
		// active1.redis.port=6311
		return "${" + name + ".redis}";
		// return "${" + name + ".redis.host}";
		// return "${" + name + ".redis.host}:${" + name + ".redis.port}";
	}
}