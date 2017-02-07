package io.leopard.web4j.nobug.csrf;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

/**
 * 是办公室IP才开放.
 * 
 * @author 阿海
 *
 */
public class CsrfCheckerOfficeIpImpl implements CsrfChecker {

	private static Set<String> OFFICE_IP_SET = new HashSet<String>();
	static {
		// TODO ahai 这里写死了办公室IP
		OFFICE_IP_SET.add("113.106.251.82");
		OFFICE_IP_SET.add("127.0.0.1");
	}

	protected boolean isOfficeIp(String proxyIp) {
		if (OFFICE_IP_SET.contains(proxyIp)) {
			return true;
		}
		if (proxyIp.startsWith("172.17")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier) {
		String proxyIp = CsrfRequestUtil.getProxyIp(request);
		return this.isOfficeIp(proxyIp);
	}

}
