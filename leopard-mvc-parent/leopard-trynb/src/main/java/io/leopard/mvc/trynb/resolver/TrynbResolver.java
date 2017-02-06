package io.leopard.mvc.trynb.resolver;

import io.leopard.mvc.trynb.model.TrynbInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

public interface TrynbResolver {

	ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception exception, TrynbInfo trynbInfo);
}
