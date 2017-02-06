package io.leopard.security.admin;

import javax.servlet.http.HttpServletRequest;

import io.leopard.web.passport.PassportChecker;
import io.leopard.web.servlet.RequestUtil;

public class PassportCheckerAdminImpl implements PassportChecker {

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		// System.err.println("PassportCheckerAdminImpl isNeedCheckLogin:" + request.getRequestURI());
		if (isAdminFolder(request)) {
			return true;
		}
		return null;
	}

	public static boolean isAdminFolder(HttpServletRequest request) {
		String contextUri = RequestUtil.getRequestContextUri(request);
		return contextUri.startsWith("/admin/");
	}
}
