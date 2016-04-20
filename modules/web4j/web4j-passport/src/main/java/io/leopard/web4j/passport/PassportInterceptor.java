package io.leopard.web4j.passport;

import io.leopard.burrow.AutoResource;
import io.leopard.web4j.servlet.RequestUtil;
import io.leopard.web4j.servlet.UriListChecker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 检查是否已登录.
 * 
 * @author 阿海
 * 
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static Set<String> parameterNameSet = new HashSet<String>();

	static {
		parameterNameSet.add("sessUid");
		parameterNameSet.add("sessUsername");
	}
	// @Autowired(required = false)
	@AutoResource
	private PassportValidateLei passportValidateLei;

	protected UriListChecker uriListChecker = new UriListChecker();// 需要做登录验证的URL列表

	private Set<String> handlerSet = new HashSet<String>();

	public void registerHandlerMethod(HandlerMethod handlerMethod) {
		MethodParameter[] parameters = handlerMethod.getMethodParameters();
		if (parameters != null) {
			for (MethodParameter parameter : parameters) {
				String parameterName = parameter.getParameterName();
				if (parameterNameSet.contains(parameterName)) {
					// System.err.println("PassportInterceptor registerHandlerMethod" + handlerMethod + " code:" + handlerMethod.hashCode());
					handlerSet.add(handlerMethod.toString());
				}
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!this.isCheckLogin(request, handler)) {
			return true;
		}
		Object account = this.login(request, response);
		if (account == null) {
			this.showLoginBox(request, response);
			return false;
		}
		// FrequencyInterceptor.setAccount(request, account);
		request.setAttribute("account", account);
		return true;
	}

	/**
	 * 检查当前请求是否需要验证登陆.
	 * 
	 * @param request
	 * @param handler
	 * @return
	 */
	protected boolean isCheckLogin(HttpServletRequest request, Object handler) {
		// System.err.println("PassportInterceptor handler:" + handler);
		if (handlerSet.contains(handler.toString())) {
			return true;
		}
		if (uriListChecker.exists(RequestUtil.getRequestContextUri(request))) {
			return true;
		}
		return false;
	}

	/**
	 * 转到登录页面.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		String ip = RequestUtil.getProxyIp(request);
		String message = "您[" + ip + "]未登录,uri:" + request.getRequestURI();
		logger.info(message);

		passportValidateLei.showLoginBox(request, response);
	}

	protected Long login(HttpServletRequest request, HttpServletResponse response) {
		PassportUser user = SessionUtil.getUserinfo(request.getSession());
		if (user == null) {
			user = passportValidateLei.validate(request, response);
			if (user != null) {
				SessionUtil.setUserinfo(request, user);
			}
			if (user == null) {
				return null;
			}
		}
		return user.getUid();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
