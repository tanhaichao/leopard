package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.BeanAssert;
import io.leopard.web4j.trynb.model.TrynbInfo;

import org.junit.Test;

public class ErrorPageTest {

	@Test
	public void ErrorPage() {
		BeanAssert.assertModel(TrynbInfo.class);
	}

}