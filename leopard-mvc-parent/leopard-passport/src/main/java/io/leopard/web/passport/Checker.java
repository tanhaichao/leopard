package io.leopard.web.passport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface Checker {

	/**
	 * 是否需要做登陆检查?
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	List<PassportValidator> isNeedCheckLogin(HttpServletRequest request, Object handler);
}
