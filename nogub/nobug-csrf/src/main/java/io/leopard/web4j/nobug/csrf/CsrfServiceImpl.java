package io.leopard.web4j.nobug.csrf;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;

public class CsrfServiceImpl implements CsrfService {

	protected Log logger = LogFactory.getLog(this.getClass());

	public static final String publicKey = "csrfTokenKeyxx123";

	public static final String PARAMETER_NAME_CSRF_TOKEN = "csrf-token";

	private static boolean enable = true;

	private static final CsrfDao csrfDao = new CsrfDaoImpl();
	private static final TokenVerifier tokenVerifier = new TokenVerifier(csrfDao);

	private final CsrfChecker csrfChecker = new CsrfCheckerImpl();

	@Override
	public boolean isEnable() {
		return enable;
	}

	public static void setEnable(boolean enable) {
		CsrfServiceImpl.enable = enable;
	}

	public static void setOnlyLog(boolean onlyLog) {
		tokenVerifier.setOnlyLog(onlyLog);
	}

	@Override
	public void check(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) {
		if (csrfChecker.isSafe(handlerMethod, request, response, tokenVerifier)) {
			return;
		}

		Method method = handlerMethod.getMethod();
		Class<?> returnType = method.getReturnType();
		String className = returnType.getName();
		// TODO ahai className暂时写死了
		// if (returnType.equals(JsonView.class)) {
		if ("io.leopard.web4j.view.JsonView".equals(className)) {
			this.checkByJsonView(handlerMethod, request, response);
		}
		// else if (returnType.equals(UpdatedRedirectView.class)) {
		else if ("io.leopard.web4j.view.UpdatedRedirectView".equals(className)) {
			tokenVerifier.verify(request, response);
		}
		else {
			tokenVerifier.verify(request, response);
		}
	}

	protected void checkByJsonView(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) {
		if (this.isNotEmpty(request.getParameter("callback"))) {
			tokenVerifier.verify(request, response);
		}
		else if (this.isNotEmpty(request.getParameter("var"))) {
			tokenVerifier.verify(request, response);
		}
		else {
			String token = request.getParameter(PARAMETER_NAME_CSRF_TOKEN);
			if (this.isNotEmpty(token) && !"null".equals(token)) {
				tokenVerifier.verify(request, response);
			}
		}
	}

	private boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	@Override
	public String makeToken(String username, long yyuid) {
		long time = System.currentTimeMillis();
		String usernameSha1 = csrfDao.encrypt(time + " " + username + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		String yyuidSha1 = csrfDao.encrypt(time + " " + yyuid + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		String result = usernameSha1.substring(0, 10) + "-" + yyuidSha1.substring(0, 10) + "-" + time;
		return result;
	}

}
