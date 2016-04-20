package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class FtlViewTest {

	@Test
	public void FtlView() {

	}

	@Test
	public void getContentType() {
		FtlView view = new FtlView("test");
		Assert.assertEquals("text/html; charset=UTF-8", view.getContentType());
	}

}