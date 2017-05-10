package io.leopard.htdocs;

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

	// @Override
	// protected MediaType getMediaType(HttpServletRequest request, Resource resource) {
	// String uri = request.getRequestURI();
	// if (uri.endsWith(".swf")) {// TODO 特殊实现
	// return MediaType.APPLICATION_OCTET_STREAM;
	// }
	// return super.getMediaType(request, resource);
	// }

	@Override
	protected Resource getResource(HttpServletRequest request) throws IOException {
		return resource;
	}

}
