package io.leopard.htdocs;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class LeopardResourceHttpRequestHandler extends ResourceHttpRequestHandler {

	private Resource resource;

	public LeopardResourceHttpRequestHandler(ServletContext servletContext, Resource resource) {
		this.resource = resource;

		this.setServletContext(servletContext);
	}

	@Override
	protected MediaType getMediaType(HttpServletRequest request, Resource resource) {
		if (resource instanceof MediaTypeResource) {
			MediaType mediaType = ((MediaTypeResource) resource).getMediaType(request);
			if (mediaType != null) {
				return mediaType;
			}
		}
		return super.getMediaType(request, resource);
	}

	@Override
	protected Resource getResource(HttpServletRequest request) throws IOException {
		return resource;
	}

}
