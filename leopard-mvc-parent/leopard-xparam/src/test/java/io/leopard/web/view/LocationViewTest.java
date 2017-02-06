package io.leopard.web.view;

import io.leopard.web.view.LocationView;

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