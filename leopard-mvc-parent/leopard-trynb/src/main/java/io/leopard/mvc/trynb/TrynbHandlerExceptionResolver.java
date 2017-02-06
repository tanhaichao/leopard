package io.leopard.mvc.trynb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.mvc.trynb.model.TrynbInfo;
import io.leopard.mvc.trynb.resolver.TrynbResolver;
import io.leopard.mvc.trynb.resolver.TrynbResolverImpl;

public class TrynbHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	private TrynbService errorPageService = new TrynbServiceImpl();
	private TrynbResolver trynbResolver = new TrynbResolverImpl();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		if (exception == null) {
			throw new NullPointerException("exception参数怎么会为null?");
		}
		// logger.error("resolveException:" + exception.toString());
		if (!(handler instanceof HandlerMethod)) {
			return null;
		}
		String uri = request.getRequestURI();

		
		TrynbInfo trynbInfo = errorPageService.parse(request, uri, exception);
		ModelAndView view = trynbResolver.resolveView(request, response, ((HandlerMethod) handler), exception, trynbInfo);

		request.setAttribute("exception", exception);
		// request.setAttribute("modelAndView", view);
		return view;
	}

}
