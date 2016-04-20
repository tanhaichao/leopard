package io.leopard.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.w3c.dom.Element;

import io.leopard.data4j.schema.RegisterComponentUtil;

public class TxBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String txId = element.getAttribute("id");
		String dataSourceId = element.getAttribute("jdbcId") + "DataSource";
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceTransactionManager.class);
		builder.addPropertyReference("dataSource", dataSourceId);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		return RegisterComponentUtil.registerComponent(parserContext, builder, txId);
	}

}
