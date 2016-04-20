package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 多hosts.
 * 
 * @author 阿海
 *
 */
public class MultiHostRequestMappingInfoBuilder implements RequestMappingInfoBuilder {

	@Override
	public void getHeaders(RequestMapping annotation, Method method, Map<String, String> headers) {
		if ("login".equals(method.getName())) {
			return;
		}

		if (headers.containsKey("Host")) {
			return;
		}
		headers.put("Host", "vip.leopard.io");
	}

}
