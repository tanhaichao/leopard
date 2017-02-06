package io.leopard.web.passport;

import javax.servlet.http.HttpServletRequest;

public interface PassportChecker {

	/**
	 * 是否需要做登陆检查?
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	Boolean isNeedCheckLogin(HttpServletRequest request, Object handler);

}
