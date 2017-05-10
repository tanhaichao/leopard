package io.leopard.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;

import io.leopard.htdocs.ResourcesDispatcherServlet;

public class OptionsDispatcherServlet extends ResourcesDispatcherServlet {

	private static final long serialVersionUID = 1L;

	private OptionsHandler optionsHandler;

	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext context = super.initWebApplicationContext();
		try {
			optionsHandler = context.getBean(OptionsHandler.class);
		}
		catch (NoSuchBeanDefinitionException e) {

		}
		return context;
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (optionsHandler != null) {
			boolean handled = optionsHandler.doOptions(request, response);
			if (handled) {
				return;
			}
		}
		super.doOptions(request, response);
	}
}
