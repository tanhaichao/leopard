package io.leopard.schema;

import io.leopard.web4j.admin.dao.AdminLoginServiceImpl;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConfigBeanDefinitionParserIntegrationTest {

	private final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-schema.xml");

	@Test
	public void parse() {
		AdminLoginServiceImpl adminLoginService = (AdminLoginServiceImpl) ctx.getBean("adminLoginService");
		System.out.println("adminLoginService:" + adminLoginService);
	}

}