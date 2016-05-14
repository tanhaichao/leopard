package io.leopard.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data.mongo.MongoImpl;
import io.leopard.data4j.schema.RegisterComponentUtil;

/**
 * Mongo数据源标签
 * 
 * @author 阿海
 * 
 */
public class MongoDsnBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String id = element.getAttribute("id");
		String name = element.getAttribute("name");
		String database = element.getAttribute("database");
		String collectionName = element.getAttribute("collectionName");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MongoImpl.class);

		builder.addPropertyValue("server", "${" + name + ".mongo}");

		builder.addPropertyValue("database", database);
		builder.addPropertyValue("collectionName", collectionName);

		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		builder.setLazyInit(false);
		builder.setInitMethodName("init");
		builder.setDestroyMethodName("destroy");

		return RegisterComponentUtil.registerComponent(parserContext, builder, id);
	}

}