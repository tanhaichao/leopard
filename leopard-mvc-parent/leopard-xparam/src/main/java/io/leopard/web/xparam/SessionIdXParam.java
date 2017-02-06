package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取sessionId.
 * 
 * @author 阿海
 * 
 */
@Service
public class SessionIdXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String sessionId = request.getSession().getId();
		// System.err.println("sessionId:" + sessionId);
		// String sessionId = CookieUtil.getCookie(SessionUtil.SESSIONID_COOKIE_NAME, request);
		// if (StringUtils.isEmpty(sessionId)) {
		// // 页面第一次写cookie时可能会获取不到sessionId，就通过getAttribute获取
		// sessionId = (String) request.getAttribute(SessionUtil.SESSIONID_COOKIE_NAME);
		// }
		// if (StringUtils.isEmpty(sessionId)) {
		// throw new IllegalArgumentException("sessionId怎么会为空?");
		// }
		return sessionId;
	}

	@Override
	public String getKey() {
		return "sessionId";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
