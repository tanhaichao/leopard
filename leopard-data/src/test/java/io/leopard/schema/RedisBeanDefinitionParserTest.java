package io.leopard.schema;

import io.leopard.redis.RedisImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

public class RedisBeanDefinitionParserTest {

	protected RedisBeanDefinitionParser definitionParser = new RedisBeanDefinitionParser();

	protected Element element = Mockito.mock(Element.class);

	@Test
	public void getBeanClass() {

		Mockito.doReturn("false").when(element).getAttribute("log");
		Assert.assertEquals(RedisImpl.class, definitionParser.getBeanClass(element));
	}

	// String server = element.getAttribute("server");
	// String maxActive = element.getAttribute("maxActive");
	// String enableBackup = element.getAttribute("enableBackup");
	// String timeout = element.getAttribute("timeout");
	//
	// if (StringUtils.hasText(server)) {
	// builder.addPropertyValue("server", server);
	// }
	// if (StringUtils.hasText(maxActive)) {
	// builder.addPropertyValue("maxActive", Integer.valueOf(maxActive));
	// }
	//
	// if (StringUtils.hasText(enableBackup)) {
	// builder.addPropertyValue("enableBackup", Boolean.valueOf(enableBackup));
	// }
	// if (StringUtils.hasText(timeout)) {
	// builder.addPropertyValue("timeout", Integer.valueOf(timeout));
	// }
	//
	// builder.setInitMethodName("init");
	// builder.setDestroyMethodName("destroy");

	@Test
	public void doParse() {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();

		Mockito.doReturn("server").when(element).getAttribute("server");
		Mockito.doReturn("1").when(element).getAttribute("maxActive");
		Mockito.doReturn("1").when(element).getAttribute("timeout");
		Mockito.doReturn("true").when(element).getAttribute("enableBackup");

		definitionParser.doParse(element, builder);
		Assert.assertEquals("server", getPropertyValue(builder, "server"));
		Assert.assertEquals(new Integer(1), getPropertyValue(builder, "maxActive"));
		Assert.assertEquals(new Integer(1), getPropertyValue(builder, "timeout"));
		Assert.assertEquals(new Boolean(true), getPropertyValue(builder, "enableBackup"));
	}

	@Test
	public void doParse2() {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();

		Mockito.doReturn("").when(element).getAttribute("server");
		Mockito.doReturn("").when(element).getAttribute("maxActive");
		Mockito.doReturn("").when(element).getAttribute("timeout");
		Mockito.doReturn("").when(element).getAttribute("enableBackup");

		definitionParser.doParse(element, builder);
		Assert.assertNull(getPropertyValue(builder, "server"));
		Assert.assertNull(getPropertyValue(builder, "maxActive"));
		Assert.assertNull(getPropertyValue(builder, "timeout"));
		Assert.assertNull(getPropertyValue(builder, "enableBackup"));
	}

	protected Object getPropertyValue(BeanDefinitionBuilder builder, String propertyName) {
		PropertyValue propertyValue = builder.getBeanDefinition().getPropertyValues().getPropertyValue(propertyName);
		if (propertyValue == null) {
			return null;
		}
		return propertyValue.getValue();
	}
}