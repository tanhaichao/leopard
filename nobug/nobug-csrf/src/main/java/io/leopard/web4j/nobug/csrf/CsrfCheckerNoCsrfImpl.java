package io.leopard.web4j.nobug.csrf;

import io.leopard.web4j.nobug.csrf.annotation.Csrf;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public class CsrfCheckerNoCsrfImpl implements CsrfChecker {

	@Override
	public boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier) {
		Method method = handlerMethod.getMethod();
		Csrf csrf = method.getAnnotation(Csrf.class);
		if (csrf == null) {
			return true;
		}
		if (csrf.enable()) {
			return false;
		}
		return true;
	}
}
