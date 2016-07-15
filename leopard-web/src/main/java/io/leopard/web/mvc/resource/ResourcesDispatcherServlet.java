package io.leopard.web.mvc.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import io.leopard.web.servlet.ResourceHandler;
import io.leopard.web.servlet.ResourceTransformer;

public class ResourcesDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	private List<ResourceTransformer> transformers;
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
		{
			Map<String, ResourceTransformer> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ResourceTransformer.class, true, false);
			if (!matchingBeans.isEmpty()) {
				this.transformers = new ArrayList<ResourceTransformer>(matchingBeans.values());
				AnnotationAwareOrderComparator.sort(this.transformers);
			}
		}
		return context;
	}

	protected void process(HttpServletRequest request, HttpServletResponse response, Resource resource) throws ServletException, IOException {
		if (transformers != null) {
			for (ResourceTransformer transformer : transformers) {
				transformer.transform(request, resource);
			}
		}

		LeopardResourceHttpRequestHandler handler = new LeopardResourceHttpRequestHandler(super.getServletContext(), resource);
		handler.handleRequest(request, response);
	}

	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uri = request.getRequestURI();
		// System.err.println("noHandlerFound start:" + uri);
		if (resourceHandlers != null) {
			for (ResourceHandler handler : resourceHandlers) {
				Resource resource = handler.doHandler(uri, request, response);
				if (resource != null && resource.exists()) {
					this.process(request, response, resource);
					return;
				}
			}
		}
		// System.err.println("noHandlerFound end:" + uri);
		super.noHandlerFound(request, response);
	}
}
