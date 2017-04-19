package io.leopard.web.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

public class RequestMappingUtil {

	/**
	 * 根据请求方法获取URL
	 * 
	 * @param method
	 * @return
	 */
	public static String getMethodUrl(HandlerMethod method) {
		String methodValue = getMethodValue(method);
		Class<?> beanType = method.getBeanType();
		RequestMapping anno = beanType.getAnnotation(RequestMapping.class);
		if (anno == null || anno.value().length == 0) {
			return methodValue;
		}
		else {
			String url = anno.value()[0] + methodValue;
			url = url.replace("//", "/");
			return url;
		}
	}

	protected static String getMethodValue(HandlerMethod method) {
		RequestMapping anno = method.getMethodAnnotation(RequestMapping.class);
		if (anno == null || anno.value().length == 0) {
			return "/" + method.getMethod().getName();
		}
		else {
			return anno.value()[0];
		}
	}
}
