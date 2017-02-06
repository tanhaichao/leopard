package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取cookie中的uid(经过通行证登录验证)
 * 
 * @author 阿海
 * 
 */

@Service
public class CookieLoginedUidXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		throw new UnsupportedOperationException("Not Impl.");
	}

	@Override
	public String getKey() {
		return "cookieLoginedUid";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
