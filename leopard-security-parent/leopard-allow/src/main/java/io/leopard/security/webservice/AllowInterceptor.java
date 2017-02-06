package io.leopard.security.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import io.leopard.security.allow.AllowService;
import io.leopard.web.servlet.RegisterHandlerInterceptor;
import io.leopard.web.servlet.RequestUtil;

/**
 * Webservice访问权限检查.
 * 
 * @author 阿海
 * 
 */
@Component
@Order(9)
public class AllowInterceptor extends RegisterHandlerInterceptor {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AllowService allowService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// logger.info("preHandle uri:" + request.getRequestURI() + " handler:" + handler.getClass());

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod method = (HandlerMethod) handler;
		Webservice webservice = method.getMethodAnnotation(Webservice.class);
		// logger.info("preHandle method:" + method + " webservice:" + webservice);
		if (webservice == null) {
			return true;
		}

		this.check(request);
		return true;
	}

	protected void check(HttpServletRequest request) {
		String requestUri = RequestUtil.getRequestContextUri(request);
		String proxyIp = RequestUtil.getProxyIp(request);// TODO 这里要去掉CDN的IP
		logger.info("check requestUri:" + requestUri + " proxyIp:" + proxyIp);

		allowService.checkAllow(requestUri, proxyIp);
	}

}
