package io.leopard.web4j.parameter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

/**
 * 获取浏览器类型(User-Agent).
 * 
 * @author 阿海
 * 
 */
@Service
public class UserAgentPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return userAgent;
	}

	@Override
	public String getKey() {
		return "userAgent";
	}

}
