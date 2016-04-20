package io.leopard.web4j.parameter;

import io.leopard.web4j.servlet.RequestUtil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class CookieUsernamePageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		String cookieUsername = RequestUtil.getCookieUsername(request);
		return cookieUsername;
	}

	@Override
	public String getKey() {
		return "cookieUsername";
	}

}
