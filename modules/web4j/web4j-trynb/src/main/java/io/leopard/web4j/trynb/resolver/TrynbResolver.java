package io.leopard.web4j.trynb.resolver;

import io.leopard.web4j.trynb.model.TrynbInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface TrynbResolver {

	ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception, TrynbInfo trynbInfo);
}
