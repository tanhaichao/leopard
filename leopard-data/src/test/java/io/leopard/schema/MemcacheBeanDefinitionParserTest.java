package io.leopard.schema;

import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Element;

public class MemcacheBeanDefinitionParserTest {

	protected MemcacheBeanDefinitionParser befinitionParser = new MemcacheBeanDefinitionParser();

	protected Element element = Mockito.mock(Element.class);

	@Test
	public void createRedisImpl() {
		// AUTO
	}

	// @Test
	// public void doParse() {
	// {
	// Mockito.doReturn("server:11211").when(this.element).getAttribute("server");
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// Assert.assertEquals("server:11211", (String) builder.getBeanDefinition().getPropertyValues().getPropertyValue("server").getValue());
	// }
	// {
	// Mockito.doReturn(null).when(this.element).getAttribute("server");
	// // Mockito.doReturn("15").when(this.element).getAttribute("maxPoolSize");
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// Assert.assertNull(builder.getBeanDefinition().getPropertyValues().getPropertyValue("server"));
	// }
	// }
	//
	// @Test
	// public void doParse2() {
	//
	// Mockito.doReturn("server:11211").when(this.element).getAttribute("server");
	// Mockito.doReturn("memcached").when(this.element).getAttribute("type");
	// Mockito.doReturn("true").when(this.element).getAttribute("sanitizeKeys");
	// Mockito.doReturn("40").when(this.element).getAttribute("maxConn");
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// Assert.assertEquals("server:11211", (String) builder.getBeanDefinition().getPropertyValues().getPropertyValue("server").getValue());
	// Assert.assertEquals("true", (String) builder.getBeanDefinition().getPropertyValues().getPropertyValue("sanitizeKeys").getValue());
	// Assert.assertEquals("40", (String) builder.getBeanDefinition().getPropertyValues().getPropertyValue("maxConn").getValue());
	//
	// }
	//
	// @Test
	// public void doParse3() {
	//
	// Mockito.doReturn("server:11211").when(this.element).getAttribute("server");
	// Mockito.doReturn("memcached").when(this.element).getAttribute("type");
	// Mockito.doReturn(null).when(this.element).getAttribute("sanitizeKeys");
	// Mockito.doReturn(null).when(this.element).getAttribute("maxConn");
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// Assert.assertEquals("server:11211", (String) builder.getBeanDefinition().getPropertyValues().getPropertyValue("server").getValue());
	// Assert.assertNull(builder.getBeanDefinition().getPropertyValues().getPropertyValue("sanitizeKeys"));
	// Assert.assertNull(builder.getBeanDefinition().getPropertyValues().getPropertyValue("maxConn"));
	//
	// }
}