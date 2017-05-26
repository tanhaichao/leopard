package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data.schema.RegisterComponentUtil;

/**
 * ElasticSearch数据源标签
 * 
 * @author 阿海
 * 
 */
public class SearcherDsnBeanDefinitionParser implements BeanDefinitionParser {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		try {
			Class.forName("io.leopard.elasticsearch.SearcherImpl");
		}
		catch (ClassNotFoundException e) {
			return null;
		}

		// logger.info("parse:");
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");

		String maxActive = element.getAttribute("maxActive");
		String timeout = element.getAttribute("timeout");
		// String password = element.getAttribute("password");
		// String createConnectionFactory = element.getAttribute("createConnectionFactory");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getSearcherImpl());

		if (StringUtils.isNotEmpty(maxActive)) {
			builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
		}
		if (StringUtils.isNotEmpty(timeout)) {
			builder.addPropertyValue("timeout", Integer.valueOf(timeout));
		}
		// if (StringUtils.isNotEmpty(password)) {
		// builder.addPropertyValue("password", password);
		// }
		String server = this.getServer(name);
		// System.out.println("redis-dsn server:" + server);

		builder.addPropertyValue("server", server);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

	protected String getServer(String name) {
		return "${" + name + ".search}";
	}
}