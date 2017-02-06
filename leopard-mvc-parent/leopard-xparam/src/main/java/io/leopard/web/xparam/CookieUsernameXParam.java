package io.leopard.web.xparam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

@Service
public class CookieUsernameXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String username = XParamUtil.getCookie("username", request);

		if (username == null) {
			return null;
		}
		try {
			return URLDecoder.decode(username, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	@Override
	public String getKey() {
		return "cookieUsername";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
