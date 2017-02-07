package io.leopard.web4j.nobug.csrf;

import io.leopard.web4j.nobug.csrf.annotation.NoReferer;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

public class CsrfCheckerAdminFolderImpl implements CsrfChecker {

	@Override
	public boolean isSafe(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response, TokenVerifier tokenVerifier) {
		String contextUri = CsrfRequestUtil.getRequestContextUri(request);
		// String contextUri = request.getRequestURI();

		boolean isAdminFolder = contextUri.startsWith("/admin/");
		// boolean isAdminFolder = AdminHandlerInterceptor.isAdminFolder(request);
		if (!isAdminFolder) {
			return false;
		}

		if ("/admin/index.do".equals(contextUri)) {
			// TODO ahai 管理后台目录可配置，这里暂时写死
			return true;
		}
		Method method = handlerMethod.getMethod();
		// Class<?> returnType = method.getReturnType();
		// if (returnType.equals(LocationView.class)) {
		// return;
		// }
		NoReferer noReferer = method.getAnnotation(NoReferer.class);
		// System.err.println("method:" + method.toGenericString());
		if (noReferer != null) {
			return true;
		}
		RefererSecurityValidator.checkReferer(request);
		return true;
	}

}
