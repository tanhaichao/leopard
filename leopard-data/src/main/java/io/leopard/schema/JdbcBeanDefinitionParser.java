package io.leopard.schema;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data.env.EnvUtil;
import io.leopard.data.schema.RegisterComponentUtil;

public class JdbcBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		// BeanDefinitionParserUtil.printParserContext(JdbcBeanDefinitionParser.class, parserContext);

		final String jdbcId = element.getAttribute("id");
		String dataSourceId = element.getAttribute("dataSourceId");

		if (StringUtils.isEmpty(dataSourceId)) {
			dataSourceId = jdbcId + "DataSource";
		}

		this.createDataSource(dataSourceId, element, parserContext);

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getJdbcMysqlImpl());
		builder.addPropertyReference("dataSource", dataSourceId);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		return RegisterComponentUtil.registerComponent(parserContext, builder, jdbcId);
	}

	// <bean id="masterDataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">
	// <property name="driverClass" value="${jdbc.driverClassName}" />
	// <property name="jdbcUrl" value="jdbc:mysql://${jdbc.host}:3306/notice?useUnicode=true&amp;characterEncoding=UTF8" />
	// <property name="user" value="${jdbc.username}" />
	// <property name="password" value="${jdbc.password}" />
	// <property name="testConnectionOnCheckout" value="false" />
	// <property name="initialPoolSize" value="2" />
	// <property name="minPoolSize" value="2" />
	// <property name="maxPoolSize" value="15" />
	// <property name="acquireIncrement" value="1" />
	// <property name="acquireRetryAttempts" value="1" />
	// <property name="maxIdleTime" value="7200" />
	// <property name="maxStatements" value="0" />
	// </bean>
	protected BeanDefinition createDataSource(String dataSourceId, Element element, ParserContext parserContext) {

		final String host = element.getAttribute("host");
		final String database = element.getAttribute("database");
		final String user = element.getAttribute("user");
		final String password = element.getAttribute("password");

		final String maxPoolSize = element.getAttribute("maxPoolSize");
		final String driverClass = element.getAttribute("driverClass");
		final String port = element.getAttribute("port");

		int idleConnectionTestPeriod = 0;
		if (EnvUtil.isDevEnv() && (SystemUtils.IS_OS_WINDOWS || SystemUtils.IS_OS_MAC)) {
			idleConnectionTestPeriod = 60;// 间隔60秒检查空闲连接
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceManager.getJdbcDataSource());

		builder.addPropertyValue("host", host);
		builder.addPropertyValue("database", database);
		builder.addPropertyValue("user", user);
		builder.addPropertyValue("password", password);
		builder.addPropertyValue("driverClass", driverClass);
		if (StringUtils.isNotEmpty(port)) {
			builder.addPropertyValue("port", port);
		}
		if (StringUtils.isNotEmpty(maxPoolSize)) {
			builder.addPropertyValue("maxPoolSize", maxPoolSize);
		}
		builder.addPropertyValue("idleConnectionTestPeriod", idleConnectionTestPeriod);

		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(true);
		// builder.setInitMethodName("init");
		// builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, dataSourceId);
	}

}