package io.leopard.web.passport;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import io.leopard.web.servlet.RequestUtil;
import io.leopard.web.xparam.Nologin;

/**
 * 通行证
 * 
 * @author 谭海潮
 *
 */
public class PassportUtil {

	protected static Log logger = LogFactory.getLog(PassportUtil.class);

	/**
	 * 判断handler是否需要登录检查.
	 * 
	 * @param handler
	 * @return
	 */
	public static boolean isNeedCheckLogin(PassportValidator validator, HttpServletRequest request, Object handler) {
		HandlerMethod method = (HandlerMethod) handler;
		Nologin nologin = method.getMethodAnnotation(Nologin.class);
		if (nologin != null) {
			return false;
		}
		Boolean isNeedCheckLogin = validator.isNeedCheckLogin(request, handler);
		if (isNeedCheckLogin != null) {
			return isNeedCheckLogin;
		}
		String parameterName = PassportUtil.getSessionAttributeName(validator);// 要求参数名称和session属性名称一致
		return ParameterNameResolver.hasParameterName(handler, parameterName);
	}

	/**
	 * 登录验证，并缓存到session
	 * 
	 * @param validator
	 * @param request
	 * @param response
	 * @return
	 */
	public static Object validateAndStore(PassportValidator validator, HttpServletRequest request, HttpServletResponse response) {
		PassportGroup group = PassportUtil.getPassportGroup(validator);
		String sessionAttributeName = PassportUtil.getSessionAttributeName(group);
		boolean sessionStore;// 是否在session中存储validate方法的返回值
		if (group == null) {
			sessionStore = true;
		}
		else {
			sessionStore = group.session();
		}
		// logger.info("validator:" + validator.getClass().getName() + " sessionStore:" + sessionStore + " sessionAttributeName:" + sessionAttributeName);
		if (sessionStore) {
			Object passport = request.getSession().getAttribute(sessionAttributeName);
			if (passport != null) {
				return passport;
			}
		}
		Object passport = validator.validate(request, response);
		if (sessionStore && passport != null) {
			if (passport instanceof Number) {
				Number uid = (Number) passport;
				if (uid.longValue() > 0) {
					request.getSession().setAttribute(sessionAttributeName, passport);
				}
			}
			else if (passport instanceof String) {
				String username = (String) passport;
				if (username.length() > 0) {
					request.getSession().setAttribute(sessionAttributeName, passport);
				}
			}
			else {
				throw new RuntimeException("passport不支持该类型[" + passport.getClass().getName() + "].");
			}
		}
		return passport;
	}

	/**
	 * 显示登录框
	 * 
	 * @param validator
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean showLoginBox(PassportValidator validator, HttpServletRequest request, HttpServletResponse response) {
		String sessionAttributeName = PassportUtil.getSessionAttributeName(validator);

		String ip = RequestUtil.getProxyIp(request);
		String message = "您[" + ip + "]未登录,uri:" + request.getRequestURI();
		logger.info(message);
		if (validator.showLoginBox(request, response)) {// 自定义验证器已经显示登录框
			return true;
		}

		FtlView view = new FtlView("/passport/ftl", "login");
		String url = RequestUtil.getRequestURL(request);
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) {
			url += "?" + queryString;
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		model.put("type", sessionAttributeName);
		try {
			view.render(model, request, response);
			return true;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 根据登录验证器，获取sessionKey
	 * 
	 * @param validator
	 * @return
	 */
	public static PassportGroup getPassportGroup(PassportValidator validator) {
		{
			while (true) {
				if (validator instanceof PassportValidatorWrapper) {
					validator = ((PassportValidatorWrapper) validator).getPassportValidator();
				}
				else {
					break;
				}
			}
		}
		Class<?> clazz = validator.getClass();
		while (true) {
			String name = clazz.getName();
			if (name.indexOf("$$") == -1) {
				break;
			}
			clazz = clazz.getSuperclass();
			if (clazz == null || clazz.equals(Object.class)) {
				break;
			}
		}
		PassportGroup anno = clazz.getAnnotation(PassportGroup.class);
		return anno;
		// if (anno == null || StringUtils.isEmpty(anno.value())) {
		// return "sessUid";
		// }
		// return anno.value();
	}

	/**
	 * 根据登录验证器，获取sessionKey
	 * 
	 * @param validator
	 * @return
	 */
	public static String getSessionAttributeName(PassportGroup group) {
		if (group == null || StringUtils.isEmpty(group.value())) {
			return "sessUid";
		}
		return group.value();
	}

	/**
	 * 根据登录验证器，获取sessionKey
	 * 
	 * @param validator
	 * @return
	 */
	public static String getSessionAttributeName(PassportValidator validator) {
		PassportGroup anno = getPassportGroup(validator);
		return getSessionAttributeName(anno);
	}
}
