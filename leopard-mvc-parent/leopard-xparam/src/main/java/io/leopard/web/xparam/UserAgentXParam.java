package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取浏览器类型(User-Agent).
 * 
 * @author 阿海
 * 
 */
@Service
public class UserAgentXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String userAgent = request.getHeader("user-agent");
		return userAgent;
	}

	@Override
	public String getKey() {
		return "userAgent";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
