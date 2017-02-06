package io.leopard.mvc.trynb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 * @author 阿海
 */
@Controller
public class PageNotFoundController {
	private final Log logger = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/404.do")
	public ModelAndView notfound(HttpServletRequest request) {
		if (logger.isWarnEnabled()) {
			String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
			String queryString = request.getQueryString();
			String url = "http://" + request.getServerName() + uri;
			if (queryString != null && queryString.length() > 0) {
				url += "?" + queryString;
			}
			logger.warn("pageNotFound url:" + url);
		}
		return new ModelAndView("/404");
	}

}
