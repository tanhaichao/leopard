package io.leopard.web4j.trynb;

import io.leopard.web4j.view.JsonView;
import io.leopard.web4j.view.ListJsonView;

import org.junit.Test;

public class LeopardMappingHandlerExceptionResolverTest {

	@Test
	public void resolveException() {
		boolean flag = JsonView.class.isAssignableFrom(ListJsonView.class);
		System.out.println("flag:" + flag);
	}

}