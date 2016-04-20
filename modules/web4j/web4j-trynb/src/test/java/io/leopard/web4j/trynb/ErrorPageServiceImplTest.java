package io.leopard.web4j.trynb;

import io.leopard.test4j.mock.MockRequest;

import org.junit.Test;

public class ErrorPageServiceImplTest {

	protected ErrorPageServiceImpl errorPageService = new ErrorPageServiceImpl();

	@Test
	public void error() {
		MockRequest request = new MockRequest();
		this.errorPageService.error(request, "/index.do", new Exception());
	}

}