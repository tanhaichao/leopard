package io.leopard.web.passport;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import io.leopard.web.servlet.RequestUtil;

public class PassportValidatorWrapper implements PassportValidator {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected PassportValidator validator;

	protected String sessionKey;

	public PassportValidatorWrapper(PassportValidator validator) {
		this.validator = validator;
		this.sessionKey = Finder.getSessionKey(validator);
		if (StringUtils.isEmpty(sessionKey)) {
			sessionKey = "sessUid";
		}
	}

	public PassportValidator getValidator() {
		return validator;
	}

	@Override
	public Object validate(HttpServletRequest request, HttpServletResponse response) {
		Object passport = request.getSession().getAttribute(sessionKey);
		if (passport != null) {
			return passport;
		}
		passport = validator.validate(request, response);
		if (passport != null) {
			if (passport instanceof Number) {
				Number uid = (Number) passport;
				if (uid.longValue() > 0) {
					request.getSession().setAttribute(sessionKey, passport);
				}
			}
			else if (passport instanceof String) {
				String username = (String) passport;
				if (username.length() > 0) {
					request.getSession().setAttribute(sessionKey, passport);
				}
			}
			else {
				throw new RuntimeException("passport不支持该类型[" + passport.getClass().getName() + "].");
			}
		}
		return passport;
	}

	@Override
	public boolean showLoginBox(HttpServletRequest request, HttpServletResponse response) {
		String ip = RequestUtil.getProxyIp(request);
		String message = "您[" + ip + "]未登录,uri:" + request.getRequestURI();
		logger.info(message);
		if (validator.showLoginBox(request, response)) {
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
		model.put("type", sessionKey);
		try {
			view.render(model, request, response);
			return true;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public boolean login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return validator.login(request, response);
	}

	@Override
	public Boolean isNeedCheckLogin(HttpServletRequest request, Object handler) {
		return validator.isNeedCheckLogin(request, handler);
	}

	@Override
	public String toString() {
		return "PassportValidator[" + this.sessionKey + "]";
	}
}
