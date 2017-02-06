package io.leopard.web.view;

import io.leopard.web.view.RedirectView;

import org.junit.Assert;
import org.junit.Test;

public class RedirectViewTest {

	@Test
	public void RedirectView() {
		RedirectView redirectView = new RedirectView("http://yy.com/");
		Assert.assertEquals("http://yy.com/", redirectView.getUrl());
		org.springframework.web.servlet.view.RedirectView view = (org.springframework.web.servlet.view.RedirectView) redirectView.getView();
		Assert.assertEquals("http://yy.com/", view.getUrl());
	}

}