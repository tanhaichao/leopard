package io.leopard.web.mvc.resource;

import java.io.IOException;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;

import io.leopard.web.servlet.ResourceTransformer;

public class ResourceTransformerImpl implements ResourceTransformer {
	private List<ResourceTransformer> transformers = new ArrayList<ResourceTransformer>();

	public void setBeanFactory(WebApplicationContext context) {
		{
			Map<String, ResourceTransformer> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ResourceTransformer.class, true, false);
			if (!matchingBeans.isEmpty()) {
				this.transformers = new ArrayList<ResourceTransformer>(matchingBeans.values());
				AnnotationAwareOrderComparator.sort(this.transformers);
			}
		}
	}

	@Override
	public Resource transform(HttpServletRequest request, Resource resource) throws IOException {
		if (transformers != null) {
			for (ResourceTransformer transformer : transformers) {
				resource = transformer.transform(request, resource);
			}
		}
		// System.err.println("resource:"+resource);
		return resource;
	}

}
