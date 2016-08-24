package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.web.mvc.condition.ExtensiveDomain;

/**
 * APP多版本.
 * 
 * @author 阿海
 *
 */
public class MultiVersionRequestMappingInfoBuilder implements RequestMappingInfoBuilder {

	@Override
	public void getHeaders(RequestMapping annotation, Method method, ExtensiveDomain extensiveDomain, Map<String, String> headers) {
		ResponseBody anno = method.getAnnotation(ResponseBody.class);
		if (anno == null) {
			return;
		}
		if (headers.containsKey("version")) {
			return;
		}
		headers.put("version", "0.2");
	}

}
