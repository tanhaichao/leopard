package io.leopard.schema;

import io.leopard.data.signature.SignatureServiceImpl;

import org.junit.Assert;
import org.junit.Test;

public class SignatureBeanDefinitionParserTest {

	// @Test
	// public void doParse0() {
	// ElementImpl element = new ElementImpl();
	//
	// BeanDefinitionBuilder builder = Mockito.mock(BeanDefinitionBuilder.class);
	// SignatureBeanDefinitionParser parser = new SignatureBeanDefinitionParser();
	// try {
	// parser.doParse(element, builder);
	// Assert.fail("怎么没有抛异常?");
	// }
	// catch (IllegalArgumentException e) {
	//
	// }
	// }
	//
	// @Test
	// public void doParse() {
	// ElementImpl element = new ElementImpl();
	//
	// element.setAttribute("server", "server");
	// element.setAttribute("redisKey", "redisKey");
	// element.setAttribute("publicKey", "publicKey");
	// element.setAttribute("useBase16", "false");
	// element.setAttribute("checkUsed", "false");
	// element.setAttribute("maxActive", "10");
	// element.setAttribute("initialPoolSize", "10");
	// element.setAttribute("timeout", "3000");
	// BeanDefinitionBuilder builder = Mockito.mock(BeanDefinitionBuilder.class);
	// SignatureBeanDefinitionParser parser = new SignatureBeanDefinitionParser();
	// parser.doParse(element, builder);
	// element.removeAttribute("server");
	// element.setAttribute("redis-ref", "redis");
	// parser.doParse(element, builder);
	// }
	//
	// @Test
	// public void doParse2() {
	// ElementImpl element = new ElementImpl();
	//
	// element.setAttribute("redis-ref", "redis");
	// BeanDefinitionBuilder builder = Mockito.mock(BeanDefinitionBuilder.class);
	// SignatureBeanDefinitionParser parser = new SignatureBeanDefinitionParser();
	// parser.doParse(element, builder);
	// }

	@Test
	public void getBeanClass() {
		SignatureBeanDefinitionParser parser = new SignatureBeanDefinitionParser();
		Assert.assertEquals(SignatureServiceImpl.class, parser.getBeanClass(null));
	}
}