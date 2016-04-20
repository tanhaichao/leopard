package io.leopard.aop.matcher;

import org.junit.Assert;
import org.junit.Test;

public class BeanMatcherImplTest {

	private BeanMatcherImpl beanMatcher = new BeanMatcherImpl();

	@Test
	public void matche() {
		Assert.assertTrue(beanMatcher.matche(null, "UserService", "io.leopard.web.UserServiceImpl"));
		Assert.assertTrue(beanMatcher.matche(null, "UserService", "com.yy.news.service.impl.UserServiceImpl"));
	}

}