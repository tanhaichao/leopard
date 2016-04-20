package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class OkTextViewTest {

	@Test
	public void OkTextView() {
		OkTextView view = new OkTextView("200");
		Assert.assertEquals("200", view.getMessage());
	}

}