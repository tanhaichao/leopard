package io.leopard.mvc.trynb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.mvc.trynb.model.TrynbInfo;

public class TrynbHandlerExceptionResolver implements HandlerExceptionResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	private TrynbService errorPageService = new TrynbServiceImpl();

	@Autowired
	private List<TrynbResolver> trynbResolverList;

	// @PostConstruct
	// public void init() {
	// for (TrynbResolver trynbResolver : trynbResolverList) {
	// logger.info("trynbResolver:" + trynbResolver.getClass().getName());
	// }
	// }

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
		ModelAndView view = resolveView(request, response, ((HandlerMethod) handler), exception, trynbInfo);

		request.setAttribute("exception", exception);
		// request.setAttribute("modelAndView", view);
		return view;
	}

	protected ModelAndView resolveView(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception exception, TrynbInfo trynbInfo) {
		for (TrynbResolver trynbResolver : trynbResolverList) {
			ModelAndView view = trynbResolver.resolveView(request, response, handler, exception, trynbInfo);
			if (view != null) {
				return view;
			}
		}
		String page = trynbInfo.getPage();
		if (StringUtils.isEmpty(page)) {
			page = "/error";
		}
		ModelAndView view = new ModelAndView(page);
		view.addObject("message", trynbInfo.getMessage());
		return view;
	}
}
