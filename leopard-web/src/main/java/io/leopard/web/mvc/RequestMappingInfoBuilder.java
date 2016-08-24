package io.leopard.web.mvc;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import io.leopard.web.mvc.condition.ExtensiveDomain;

public interface RequestMappingInfoBuilder {

	void getHeaders(RequestMapping annotation, Method method, ExtensiveDomain extensiveDomain, Map<String, String> headers);

}
