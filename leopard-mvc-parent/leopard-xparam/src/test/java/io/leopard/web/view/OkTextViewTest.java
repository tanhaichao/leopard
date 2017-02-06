package io.leopard.web.view;

import io.leopard.web.view.OkTextView;

import org.junit.Assert;
import org.junit.Test;

public class OkTextViewTest {

	@Test
	public void OkTextView() {
		OkTextView view = new OkTextView("200");
		Assert.assertEquals("200", view.getMessage());
	}

}