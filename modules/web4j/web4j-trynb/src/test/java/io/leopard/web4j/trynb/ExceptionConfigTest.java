package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.trynb.model.ExceptionConfig;

import org.junit.Test;

public class ExceptionConfigTest {

	@Test
	public void ExceptionConfig() {
		BeanAssert.assertModel(ExceptionConfig.class);
	}

}