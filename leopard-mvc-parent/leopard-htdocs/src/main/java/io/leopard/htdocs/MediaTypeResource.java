package io.leopard.htdocs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public interface MediaTypeResource extends Resource {
	MediaType getMediaType(HttpServletRequest request);
}
