package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class LocationViewTest {

	@Test
	public void getBody() {

		LocationView view = new LocationView("/index.do");
		Assert.assertEquals("text/html; charset=UTF-8", view.getContentType());
		Assert.assertNotNull(view.getBody(null, null));
	}
}