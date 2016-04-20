package io.leopard.web4j.trynb;

import io.leopard.web4j.trynb.model.TrynbInfo;
import io.leopard.web4j.trynb.resolver.TrynbResolver;
import io.leopard.web4j.trynb.resolver.TrynbResolverImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class TrynbMappingHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	private ErrorPageService errorPageService = new ErrorPageServiceImpl();
	private TrynbResolver trynbResolver = new TrynbResolverImpl();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		String uri = request.getRequestURI();

		TrynbInfo trynbInfo = errorPageService.parseErrorPage(request, uri, exception);
		return trynbResolver.resolveView(request, response, handler, exception, trynbInfo);
	}

}
