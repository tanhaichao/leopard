package io.leopard.web.proxy;

import io.leopard.web.servlet.RegisterHandlerInterceptor;
import io.leopard.web.servlet.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面代理，实现访问指定resin机器.
 * 
 * @author 阿海
 * 
 */
@Component
public class ProxyInterceptor extends RegisterHandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);
		boolean proxy = WebserverProxy.proxy(requestUri, request, response);
		if (proxy) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
