package io.leopard.data4j.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;

public class RegisterComponentUtil {

	public static BeanDefinition registerComponent(ParserContext parserContext, BeanDefinitionBuilder builder, String id) {
		BeanDefinition definition = builder.getBeanDefinition();
		if (!parserContext.isNested()) {
			String[] aliases = new String[0];
			BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, id, aliases);
			BeanDefinitionReaderUtils.registerBeanDefinition(holder, parserContext.getRegistry());
			BeanComponentDefinition componentDefinition = new BeanComponentDefinition(holder);
			parserContext.registerComponent(componentDefinition);
		}
		return definition;
	}

}
