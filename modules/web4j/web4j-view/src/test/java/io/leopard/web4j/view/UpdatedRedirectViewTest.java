package io.leopard.web4j.view;

import org.junit.Assert;
import org.junit.Test;

public class UpdatedRedirectViewTest {

	@Test
	public void UpdatedRedirectView() {
		UpdatedRedirectView view = new UpdatedRedirectView("/index.do");
		Assert.assertEquals("/index.do", view.getUrl());
	}

}