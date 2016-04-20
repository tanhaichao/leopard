package io.leopard.web4j.nobug.csrf;

import io.leopard.burrow.util.EncryptUtil;
import io.leopard.web4j.nobug.RefererSecurityValidator;
import io.leopard.web4j.nobug.annotation.Csrf;
import io.leopard.web4j.nobug.annotation.NoReferer;
import io.leopard.web4j.passport.SessionUtil;
import io.leopard.web4j.servlet.CookieUtil;
import io.leopard.web4j.servlet.RequestUtil;
import io.leopard.web4j.view.LocationView;
import io.leopard.web4j.view.UpdatedRedirectView;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.web.method.HandlerMethod;

public class CsrfServiceImpl implements CsrfService {

	protected Log logger = LogFactory.getLog(this.getClass());

	public static final String publicKey = "csrfTokenKeyxx123";

	private static Set<String> OFFICE_IP_SET = new HashSet<String>();
	static {
		OFFICE_IP_SET.add("113.106.251.82");
		OFFICE_IP_SET.add("127.0.0.1");
	}

	public static final String PARAMETER_NAME_CSRF_TOKEN = "csrf-token";

	private static boolean onlyLog = true;// 只记录日志

	private static boolean enable = true;

	// @Resource(name = "sessUsernamePageParameter")
	// private PageParameter sessUsernamePageParameter;
	//
	// @Resource(name = "sessYyuidPageParameter")
	// private PageParameter sessYyuidPageParameter;

	private final CsrfDao csrfDaoYyuidImpl = new CsrfDaoYyuidImpl();

	@Override
	public boolean isEnable() {
		return enable;
	}

	public static void setEnable(boolean enable) {
		CsrfServiceImpl.enable = enable;
	}

	public static void setOnlyLog(boolean onlyLog) {
		CsrfServiceImpl.onlyLog = onlyLog;
	}

	protected void checkAdminFolderReferer(HandlerMethod handlerMethod, HttpServletRequest request) {
		String contextUri = RequestUtil.getRequestContextUri(request);
		if ("/admin/index.do".equals(contextUri)) {
			// TODO ahai 管理后台目录可配置，这里暂时写死
			return;
		}
		Method method = handlerMethod.getMethod();
		Class<?> returnType = method.getReturnType();
		if (returnType.equals(LocationView.class)) {
			return;
		}
		NoReferer noReferer = method.getAnnotation(NoReferer.class);
		// System.err.println("method:" + method.toGenericString());
		if (noReferer != null) {
			return;
		}
		RefererSecurityValidator.checkReferer(request);
	}

	@Override
	public void check(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) {
		String contextUri = RequestUtil.getRequestContextUri(request);
		boolean isAdminFolder = contextUri.startsWith("/admin/");
		// boolean isAdminFolder = AdminHandlerInterceptor.isAdminFolder(request);
		if (isAdminFolder) {
			checkAdminFolderReferer(handlerMethod, request);
			return;
		}

		boolean isExcludeUri = CsrfUtil.isExcludeUri(request);
		if (isExcludeUri) {
			// 不需要登录的地址，不做csrf检查.
			String uri = RequestUtil.getRequestContextUri(request);
			logger.debug("URL[" + uri + "]不需要登录，不做CSRF检查.");
			return;
		}

		Method method = handlerMethod.getMethod();
		Class<?> returnType = method.getReturnType();

		Csrf csrf = method.getAnnotation(Csrf.class);
		if (csrf != null) {
			if (csrf.enable() == false) {
				// 不做CSRF检查.
				// checkAllowDomain(request, csrf);
				return;
			}
			checkToken(request, response);
		}
		else if (returnType.equals(JsonView.class)) {
			this.checkByJsonView(handlerMethod, request, response);
		}
		else if (returnType.equals(UpdatedRedirectView.class)) {
			checkToken(request, response);
		}
	}

	protected void checkByJsonView(HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotEmpty(request.getParameter("callback"))) {
			checkToken(request, response);
		}
		else if (StringUtils.isNotEmpty(request.getParameter("var"))) {
			checkToken(request, response);
		}
		else {
			String token = request.getParameter(PARAMETER_NAME_CSRF_TOKEN);
			if (StringUtils.isEmpty(token) || "null".equals(token)) {
				checkAdminFolderReferer(handlerMethod, request);
			}
			else {
				checkToken(request, response);
			}
		}
	}

	protected boolean isOfficeIp(String proxyIp) {
		if (OFFICE_IP_SET.contains(proxyIp)) {
			return true;
		}
		if (proxyIp.startsWith("172.17")) {
			return true;
		}
		return false;
	}

	@Override
	public void checkToken(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("csrf-token");
		String proxyIp = RequestUtil.getProxyIp(request);
		if (!this.isOfficeIp(proxyIp)) {
			return;
		}
		if (StringUtils.isEmpty(token) || "null".equals(token)) {
			logger.debug("checkToken ip:" + proxyIp + " token为什么会为空?");
			// throw new CsrfTokenInvalidException("token不能为空.");
			return;
		}

		if (onlyLog) {
			try {
				checkToken(request, response, token);
			}
			catch (CsrfTokenInvalidException e) {
				// ErrorUtil.error(logger, "csrf token error:" + e.getMessage(), e);
				logger.error(e.getMessage(), e);
			}
		}
		else {
			checkToken(request, response, token);
		}

	}

	protected void checkToken(HttpServletRequest request, HttpServletResponse response, String token) {
		Long yyuid = SessionUtil.getUid(request.getSession());
		if (yyuid == null) {
			String str = CookieUtil.getCookie("uid", request);
			yyuid = NumberUtils.toLong(str);
		}
		String user = yyuid.toString();
		// long sessYyuid =
		// Long.parseLong(sessYyuidPageParameter.getValue(request, response));

		csrfDaoYyuidImpl.checkToken(user, token);
	}

	@Override
	public String makeToken(String username, long yyuid) {
		long time = System.currentTimeMillis();
		return makeToken(username, yyuid, time);
	}

	protected String makeToken(String username, long yyuid, long time) {
		String usernameSha1 = EncryptUtil.sha1(time + " " + username + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		String yyuidSha1 = EncryptUtil.sha1(time + " " + yyuid + " " + CsrfServiceImpl.publicKey); // 使用SHA1加密算法
		String result = usernameSha1.substring(0, 10) + "-" + yyuidSha1.substring(0, 10) + "-" + time;
		return result;
	}

}
