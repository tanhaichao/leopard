package io.leopard.web4j.nobug.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public interface CsrfService {

	void checkToken(HttpServletRequest request, HttpServletResponse response);

	String makeToken(String username, long yyuid);

	void check(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response);

	boolean isEnable();

}
