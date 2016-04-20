package io.leopard.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data.counter.CounterRedisImpl;
import io.leopard.data4j.schema.RegisterComponentUtil;

/**
 * 计数器.
 * 
 * @author 阿海
 * 
 */
public class CounterBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		final String beanId = element.getAttribute("id");
		String redisId = element.getAttribute("redis-ref");
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CounterRedisImpl.class);
		builder.addPropertyReference("redis", redisId);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);

		BeanDefinition beanDefinition = RegisterComponentUtil.registerComponent(parserContext, builder, beanId);
		return beanDefinition;
	}

}