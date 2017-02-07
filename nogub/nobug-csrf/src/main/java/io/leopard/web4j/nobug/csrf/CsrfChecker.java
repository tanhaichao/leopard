package io.leopard.web4j.nobug.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public interface CsrfChecker {

	/**
	 * 
	 * @param handlerMethod
	 * @param request
	 * @param response
	 * @return 已确认安全返回true，未确认返回false
	 */
	boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier);
}
