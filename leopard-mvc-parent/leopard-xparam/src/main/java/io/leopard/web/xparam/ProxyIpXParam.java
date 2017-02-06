package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取离服务器最近的机器IP.
 * 
 * @author 阿海
 * 
 */
@Service
public class ProxyIpXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		return getProxyIp(request);
	}

	/**
	 * 获取代理服务器IP. .
	 * 
	 * @param request
	 * @return
	 */
	public static String getProxyIp(HttpServletRequest request) {
		String proxyIp = request.getHeader("X-Real-IP");
		if (proxyIp == null) {
			proxyIp = request.getHeader("RealIP");
		}
		if (proxyIp == null) {
			proxyIp = request.getRemoteAddr();
		}
		return proxyIp;
	}

	@Override
	public String getKey() {
		return "proxyIp";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
