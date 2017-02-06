package io.leopard.web.freemarker.template;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestHolder {

	private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = requestHolder.get();
		if (request != null) {
			return request;
		}
		// TODO ahai 这里改成可以不引入spring-context?
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			return null;
		}
		request = attributes.getRequest();
		return request;
	}

	public static void setRequest(HttpServletRequest request) {
		requestHolder.set(request);
	}
}
