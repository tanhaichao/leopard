package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.bind.annotation.RequestMapping;

public class RequestMappingInfoBuilderImpl implements RequestMappingInfoBuilder {

	private List<RequestMappingInfoBuilder> builders;

	public RequestMappingInfoBuilderImpl(ApplicationContext context) {
		Map<String, RequestMappingInfoBuilder> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, RequestMappingInfoBuilder.class, true, false);
		if (!matchingBeans.isEmpty()) {
			this.builders = new ArrayList<RequestMappingInfoBuilder>(matchingBeans.values());
			AnnotationAwareOrderComparator.sort(this.builders);
		}
	}

	@Override
	public void getHeaders(RequestMapping annotation, Method method, Map<String, String> headers) {
		if (builders == null) {
			return;
		}
		for (RequestMappingInfoBuilder builder : builders) {
			builder.getHeaders(annotation, method, headers);
		}
	}

}
