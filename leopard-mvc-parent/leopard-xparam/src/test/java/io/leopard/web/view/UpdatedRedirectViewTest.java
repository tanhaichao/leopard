package io.leopard.web.view;

import io.leopard.web.view.UpdatedRedirectView;

import org.junit.Assert;
import org.junit.Test;

public class UpdatedRedirectViewTest {

	@Test
	public void UpdatedRedirectView() {
		UpdatedRedirectView view = new UpdatedRedirectView("/index.do");
		Assert.assertEquals("/index.do", view.getUrl());
	}

}