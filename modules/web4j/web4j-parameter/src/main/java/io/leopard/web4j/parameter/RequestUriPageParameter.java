package io.leopard.web4j.parameter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class RequestUriPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		// 包含contextPath
		String requestUri = request.getRequestURI();
		return requestUri;
	}

	@Override
	public String getKey() {
		return "requestUri";
	}

}
