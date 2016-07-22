package io.leopard.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.leopard.data.schema.RegisterComponentUtil;

/**
 * 验证码.
 * 
 * @author 阿海
 * 
 */
public class CaptchaBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		final String beanId = element.getAttribute("id");
		String jdbcId = element.getAttribute("jdbc-ref");

		Class<?> clazz;
		try {
			clazz = Class.forName("io.leopard.web.captcha.kit.CaptchaServiceImpl");
		}
		catch (ClassNotFoundException e) {
			// e.printStackTrace();
			return null;
		}
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		builder.addPropertyReference("jdbc", jdbcId);
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		BeanDefinition beanDefinition = RegisterComponentUtil.registerComponent(parserContext, builder, beanId);
		return beanDefinition;
	}

}