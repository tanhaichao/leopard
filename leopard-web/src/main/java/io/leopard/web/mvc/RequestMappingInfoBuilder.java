package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

public interface RequestMappingInfoBuilder {

	void getHeaders(RequestMapping annotation, Method method, Map<String, String> headers);

}
