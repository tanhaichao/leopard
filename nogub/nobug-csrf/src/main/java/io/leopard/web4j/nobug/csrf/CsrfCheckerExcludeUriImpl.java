package io.leopard.web4j.nobug.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;

public class CsrfCheckerExcludeUriImpl implements CsrfChecker {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier) {
		boolean isExcludeUri = CsrfUtil.isExcludeUri(request);
		if (isExcludeUri) {
			// 不需要登录的地址，不做csrf检查.
			String uri = CsrfRequestUtil.getRequestContextUri(request);
			logger.debug("URL[" + uri + "]不需要登录，不做CSRF检查.");
			return true;
		}
		return false;
	}

}
