package io.leopard.mvc.trynb.model;

import io.leopard.mvc.trynb.model.ErrorConfig;

import org.junit.Test;

public class ErrorConfigTest {

	@Test
	public void ErrorConfig() {
		ErrorConfig config = new ErrorConfig();
		config.setUrl("url");
		config.setPage("page");
		config.setExceptionConfigList(null);

		config.getUrl();
		config.getPage();
		config.getExceptionConfigList();
	}

}