package io.leopard.schema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data4j.schema.RegisterComponentUtil;

/**
 * MySQL数据源标签
 * 
 * @author 阿海
 * 
 */
public class MysqlDsnBeanDefinitionParser implements BeanDefinitionParser {
	// recall.jdbc.driverClassName=
	// recall.jdbc.url=
	// recall.jdbc.username=
	// recall.jdbc.password=
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		final String jdbcId = element.getAttribute("id");
		String dataSourceId = element.getAttribute("dataSourceId");
		BeanDefinition beanDefinition = createJdbc(jdbcId, dataSourceId, element, parserContext);
		this.createNosql(jdbcId, parserContext);
		return beanDefinition;
	}

	public static String getNosqlId(String jdbcId) {
		if ("jdbc".equals(jdbcId)) {
			return "nosql";
		}
		if (jdbcId.endsWith("Jdbc")) {
			return jdbcId.replace("Jdbc", "Nosql");
		}
		return jdbcId + "Nosql";
	}

	protected BeanDefinition createNosql(String jdbcId, ParserContext parserContext) {
		String beanId = getNosqlId(jdbcId);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getNosqlMysqlImpl());
		builder.addPropertyReference("jdbc", jdbcId);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(true);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, beanId);
	}

	protected BeanDefinition createJdbc(String jdbcId, String dataSourceId, Element element, ParserContext parserContext) {
		if (StringUtils.isEmpty(dataSourceId)) {
			dataSourceId = jdbcId + "DataSource";
		}
		createDataSource(dataSourceId, element, parserContext);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getJdbcMysqlImpl());
		builder.addPropertyReference("dataSource", dataSourceId);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		// builder.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_NO);

		return RegisterComponentUtil.registerComponent(parserContext, builder, jdbcId);
	}

	private BeanDefinition createDataSource(String dataSourceId, Element element, ParserContext parserContext) {
		String name = element.getAttribute("name");

		final String maxPoolSize = element.getAttribute("maxPoolSize");
		final String port = element.getAttribute("port");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getMysqlDsnDataSource());

		builder.addPropertyValue("url", "${" + name + ".jdbc.url}");
		builder.addPropertyValue("user", "${" + name + ".jdbc.username}");
		builder.addPropertyValue("password", "${" + name + ".jdbc.password}");
		builder.addPropertyValue("driverClass", "${" + name + ".jdbc.driverClass}");

		if (StringUtils.isNotEmpty(port)) {
			builder.addPropertyValue("port", port);
		}
		if (StringUtils.isNotEmpty(maxPoolSize)) {
			builder.addPropertyValue("maxPoolSize", maxPoolSize);
		}

		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(true);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, dataSourceId);
	}
}