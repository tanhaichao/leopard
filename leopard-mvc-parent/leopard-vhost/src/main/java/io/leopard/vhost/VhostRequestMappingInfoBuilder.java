package io.leopard.vhost;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;

public class VhostRequestMappingInfoBuilder implements RequestMappingInfoBuilder {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public void getHeaders(RequestMapping annotation, Method method, ExtensiveDomain extensiveDomain, Map<String, String> headers) {
		if (method == null) {
			return;
		}

		Class<?> clazz = method.getDeclaringClass();



	}

}
