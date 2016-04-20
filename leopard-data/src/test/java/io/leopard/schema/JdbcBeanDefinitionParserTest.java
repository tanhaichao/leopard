package io.leopard.schema;

import io.leopard.test.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ParserContext.class })
public class JdbcBeanDefinitionParserTest {

	protected JdbcBeanDefinitionParser befinitionParser = new JdbcBeanDefinitionParser();
	protected Element element = Mockito.mock(Element.class);

	@Test
	public void getBeanClass() {

	}

	@Test
	public void createDataSource() {
		Mockito.doReturn("3306").when(this.element).getAttribute("port");
		Mockito.doReturn("15").when(this.element).getAttribute("maxPoolSize");

		ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		Mockito.doReturn(true).when(parserContext).isNested();

		// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		BeanDefinition beanDefinition = befinitionParser.createDataSource("jdbcDataSource", element, parserContext);
		MutablePropertyValues values = beanDefinition.getPropertyValues();

		Assert.assertEquals("3306", values.getPropertyValue("port").getValue());
		Assert.assertEquals("15", values.getPropertyValue("maxPoolSize").getValue());

		// {
		// PropertyValue maxPoolSize = builder.getBeanDefinition().getPropertyValues().getPropertyValue("maxPoolSize");
		// System.out.println("maxPoolSize:" + maxPoolSize.getValue());
		// Assert.assertEquals(15, (int) (Integer) maxPoolSize.getValue());
		// }
	}

	// @Test
	// public void doParse() {
	// Mockito.doReturn("3306").when(this.element).getAttribute("port");
	// Mockito.doReturn("15").when(this.element).getAttribute("maxPoolSize");
	//
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// {
	// PropertyValue port = builder.getBeanDefinition().getPropertyValues().getPropertyValue("port");
	// System.out.println("port:" + port.getValue());
	// Assert.assertEquals(3306, (int) (Integer) port.getValue());
	// }
	// {
	// PropertyValue maxPoolSize = builder.getBeanDefinition().getPropertyValues().getPropertyValue("maxPoolSize");
	// System.out.println("maxPoolSize:" + maxPoolSize.getValue());
	// Assert.assertEquals(15, (int) (Integer) maxPoolSize.getValue());
	// }
	// }

	@Test
	public void parse() {
		Mockito.doReturn("3306").when(this.element).getAttribute("port");
		Mockito.doReturn("15").when(this.element).getAttribute("maxPoolSize");

		ParserContext parserContext = PowerMockito.mock(ParserContext.class);
		Mockito.doReturn(true).when(parserContext).isNested();

		// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
		BeanDefinition beanDefinition = befinitionParser.parse(element, parserContext);
		// MutablePropertyValues values = beanDefinition.getPropertyValues();

		Assert.assertNotNull(beanDefinition.getPropertyValues().getPropertyValue("dataSource").getValue());

	}

	// @Test
	// public void doParse2() {
	// Mockito.doReturn(null).when(this.element).getAttribute("port");
	// Mockito.doReturn(null).when(this.element).getAttribute("maxPoolSize");
	//
	// BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
	// befinitionParser.doParse(element, builder);
	// Assert.assertNull(builder.getBeanDefinition().getPropertyValues().getPropertyValue("port"));
	// Assert.assertNull(builder.getBeanDefinition().getPropertyValues().getPropertyValue("maxPoolSize"));
	// }
}