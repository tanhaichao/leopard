package io.leopard.vhost;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * APP多版本.
 * 
 * @author 阿海
 *
 */
public class MultiVersionRequestMappingInfoBuilder implements RequestMappingInfoBuilder {
	
	// TODO 测试类

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
