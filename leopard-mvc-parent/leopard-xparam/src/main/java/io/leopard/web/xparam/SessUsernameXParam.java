package io.leopard.web.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取存在session中的用户名.
 * 
 * @author 阿海
 * 
 */
@Service
public class SessUsernameXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String sessUsername = (String) request.getSession().getAttribute("passport");
		if (sessUsername == null) {
			String ip = XParamUtil.getProxyIp(request);
			throw new NotLoginException("您[" + ip + "]未登录.");

		}
		return sessUsername;
	}

	@Override
	public String getKey() {
		return "sessUsername";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }
}
