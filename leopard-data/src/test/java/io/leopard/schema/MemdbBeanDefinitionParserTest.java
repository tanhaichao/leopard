package io.leopard.schema;

import io.leopard.data4j.memdb.MemdbRsyncImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class MemdbBeanDefinitionParserTest {

	protected MemdbBeanDefinitionParser beanDefinitionParser = new MemdbBeanDefinitionParser();

	private Element element = Mockito.mock(Element.class);

	@Test
	public void getBeanClass() {
		Assert.assertEquals(MemdbRsyncImpl.class, beanDefinitionParser.getBeanClass(element));
	}

	@Test
	public void parse() {
		MemdbBeanDefinitionParser beanDefinitionParser = Mockito.spy(new MemdbBeanDefinitionParser());
		Mockito.doReturn("1").when(this.element).getAttribute("maxSize");
		Mockito.doReturn("redis").when(this.element).getAttribute("redis-ref");

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();

		beanDefinitionParser.doParse(element, builder);

		RuntimeBeanReference redisReference = (RuntimeBeanReference) builder.getBeanDefinition().getPropertyValues().getPropertyValue("redis").getValue();
		Assert.assertEquals("redis", redisReference.getBeanName());
	}
}