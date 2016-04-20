package io.leopard.web4j.method;

import io.leopard.burrow.LeopardLei;

import java.lang.reflect.Method;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public interface HandlerMethodRegisterLei extends LeopardLei {

	void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping, String uri);
}
