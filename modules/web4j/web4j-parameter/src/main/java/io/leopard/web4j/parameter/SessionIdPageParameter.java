package io.leopard.web4j.parameter;

import io.leopard.web4j.passport.SessionUtil;
import io.leopard.web4j.servlet.CookieUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 获取sessionId.
 * 
 * @author 阿海
 * 
 */
@Service
public class SessionIdPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		String sessionId = CookieUtil.getCookie(SessionUtil.SESSIONID_COOKIE_NAME, request);
		if (StringUtils.isEmpty(sessionId)) {
			// 页面第一次写cookie时可能会获取不到sessionId，就通过getAttribute获取
			sessionId = (String) request.getAttribute(SessionUtil.SESSIONID_COOKIE_NAME);
		}
		if (StringUtils.isEmpty(sessionId)) {
			throw new IllegalArgumentException("sessionId怎么会为空?");
		}
		return sessionId;
	}

	@Override
	public String getKey() {
		return "sessionId";
	}

}
