package io.leopard.htdocs;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;

import io.leopard.web.servlet.ResourceHandler;

public class ResourcesDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	private ResourceTransformerImpl transformer = new ResourceTransformerImpl();

	private List<ResourceHandler> resourceHandlers;

	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext context = super.initWebApplicationContext();
		{
			Map<String, ResourceHandler> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ResourceHandler.class, true, false);
			if (!matchingBeans.isEmpty()) {
				this.resourceHandlers = new ArrayList<ResourceHandler>(matchingBeans.values());
				AnnotationAwareOrderComparator.sort(this.resourceHandlers);
			}
		}

		this.transformer.setBeanFactory(context);

		return context;
	}

	protected String processPath(String path) {
		boolean slash = false;
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				slash = true;
			}
			else if (path.charAt(i) > ' ' && path.charAt(i) != 127) {
				if (i == 0 || (i == 1 && slash)) {
					return path;
				}
				path = slash ? "/" + path.substring(i) : path.substring(i);
				if (logger.isTraceEnabled()) {
					logger.trace("Path after trimming leading '/' and control characters: " + path);
				}
				return path;
			}
		}
		return (slash ? "/" : "");
	}

	protected boolean isInvalidPath(String path) {
		if (logger.isTraceEnabled()) {
			logger.trace("Applying \"invalid path\" checks to path: " + path);
		}
		if (path.contains("WEB-INF") || path.contains("META-INF")) {
			if (logger.isTraceEnabled()) {
				logger.trace("Path contains \"WEB-INF\" or \"META-INF\".");
			}
			return true;
		}
		if (path.contains(":/")) {
			String relativePath = (path.charAt(0) == '/' ? path.substring(1) : path);
			if (ResourceUtils.isUrl(relativePath) || relativePath.startsWith("url:")) {
				if (logger.isTraceEnabled()) {
					logger.trace("Path represents URL or has \"url:\" prefix.");
				}
				return true;
			}
		}
		if (path.contains("../")) {
			path = StringUtils.cleanPath(path);
			if (path.contains("../")) {
				if (logger.isTraceEnabled()) {
					logger.trace("Path contains \"../\" after call to StringUtils#cleanPath.");
				}
				return true;
			}
		}
		return false;
	}

	protected Resource getResource(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (path == null) {
			throw new IllegalStateException("Required request attribute '" + HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE + "' is not set");
		}
		path = processPath(path);
		if (!StringUtils.hasText(path) || isInvalidPath(path)) {
			if (logger.isTraceEnabled()) {
				logger.trace("Ignoring invalid resource path [" + path + "]");
			}
			return null;
		}
		if (path.contains("%")) {
			try {
				// Use URLDecoder (vs UriUtils) to preserve potentially decoded UTF-8 chars
				if (isInvalidPath(URLDecoder.decode(path, "UTF-8"))) {
					if (logger.isTraceEnabled()) {
						logger.trace("Ignoring invalid resource path with escape sequences [" + path + "].");
					}
					return null;
				}
			}
			catch (IllegalArgumentException ex) {
				// ignore
			}
		}
		if (resourceHandlers != null) {
			for (ResourceHandler handler : resourceHandlers) {
				// logger.info("path:" + path);
				Resource resource = handler.doHandler(path, request, response);
				if (resource != null && resource.exists()) {
					logger.info("handler:" + handler.getClass().getSimpleName() + " resource:" + resource.exists() + " path:" + path);
					return resource;
				}
			}
		}
		return null;
	}

	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getRequestURI();
		logger.warn("noHandlerFound start:" + path);
		Resource resource = this.getResource(path, request, response);
		if (resource != null && resource.exists()) {
			this.transform(request, response, resource);
			return;
		}
		logger.warn("noHandlerFound end:" + path);
		super.noHandlerFound(request, response);
	}

	protected void transform(HttpServletRequest request, HttpServletResponse response, Resource resource) throws ServletException, IOException {
		resource = transformer.transform(request, resource);
		LeopardResourceHttpRequestHandler handler = new LeopardResourceHttpRequestHandler(super.getServletContext(), resource);

		try {
			handler.afterPropertiesSet();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			handler.handleRequest(request, response);
		}
		catch (NullPointerException e) {
			logger.error("uri:" + request.getRequestURI());
			throw e;
		}
	}
}
