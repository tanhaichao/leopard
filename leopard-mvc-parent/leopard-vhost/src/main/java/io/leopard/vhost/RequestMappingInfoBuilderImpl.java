package io.leopard.vhost;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.bind.annotation.RequestMapping;

public class RequestMappingInfoBuilderImpl implements RequestMappingInfoBuilder {

	protected Log logger = LogFactory.getLog(this.getClass());

	private List<RequestMappingInfoBuilder> builders;

	public RequestMappingInfoBuilderImpl(ApplicationContext context) {
		Map<String, RequestMappingInfoBuilder> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context, RequestMappingInfoBuilder.class, true, false);
		// System.err.println("matchingBeans:" + context.getBeansOfType(RequestMappingInfoBuilder.class));
		if (!matchingBeans.isEmpty()) {
			this.builders = new ArrayList<RequestMappingInfoBuilder>(matchingBeans.values());
			AnnotationAwareOrderComparator.sort(this.builders);
		}
	}

	@Override
	public void getHeaders(RequestMapping annotation, Method method, ExtensiveDomain extensiveDomain, Map<String, String> headers) {
		if (builders == null) {
			return;
		}
		for (RequestMappingInfoBuilder builder : builders) {
			// if (method != null) {
			// logger.info("builder:" + builder + " Host:" + headers.get("Host") + " method:" + method.toGenericString());
			// }
			builder.getHeaders(annotation, method, extensiveDomain, headers);
		}
	}

}
