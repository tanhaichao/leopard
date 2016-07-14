package io.leopard.web.mvc.resource;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class LeopardResourceHttpRequestHandler extends ResourceHttpRequestHandler {

	private Resource resource;

	public LeopardResourceHttpRequestHandler(ServletContext servletContext, Resource resource) {
		this.resource = resource;
		
		this.setServletContext(servletContext);
	}

	@Override
	protected Resource getResource(HttpServletRequest request) throws IOException {
		return resource;
	}
	
	
}
