package io.leopard.web.captcha;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import io.leopard.core.exception.forbidden.CaptchaWrongException;

public class CaptchaUtil {

	private static final String SESSION_KEY = "sessCaptcha";

	protected static Log logger = LogFactory.getLog(CaptchaUtil.class);

	// private static String getSessionKey(HttpServletRequest request) {
	// String captchaGroupId = (String) request.getAttribute("captchaGroupId");
	// return getSessionKey(captchaGroupId);
	// }

	// @Deprecated
	// public static void checkCaptcha(String captcha, String sessCaptcha) throws CaptchaWrongException {
	// if (StringUtils.isEmpty(captcha)) {
	// throw new CaptchaInvalidException("验证码不能为空.");
	// }
	// if (StringUtils.isEmpty(sessCaptcha)) {
	// throw new CaptchaInvalidException("验证码未生成，验证码使用");
	// }
	// if (!captcha.equals(sessCaptcha)) {
	// logger.warn("错误验证码 sessCaptcha:" + sessCaptcha + " captcha:" + captcha);
	// throw new CaptchaWrongException("sess:" + sessCaptcha + " param:" + captcha);
	// }
	// }

	private static String getSessionKey(String captchaGroupId) {
		if (captchaGroupId == null || captchaGroupId.length() == 0) {
			return SESSION_KEY;
		}
		else {
			return SESSION_KEY + ":" + captchaGroupId;
		}
	}

	public static String getCaptchaGroupId(HttpServletRequest request) {
		return (String) request.getAttribute("captchaGroupId");
	}

	public static void saveSession(HttpServletRequest request, String captchaGroupId, String code) {
		String sessionKey = getSessionKey(captchaGroupId);
		// System.out.println("saveSession:" + sessionKey + " captchaGroupId:" + captchaGroupId + " code:" + code);
		request.getSession().setAttribute(sessionKey, code);
	}

	public static String getCode(HttpServletRequest request) {
		String sessionKey = getSessionKey(getCaptchaGroupId(request));
		return (String) request.getSession().getAttribute(sessionKey);
	}

	public static String getCode(HttpServletRequest request, String captchaGroupId) {
		String sessionKey = getSessionKey(captchaGroupId);
		// System.out.println("getCode:" + sessionKey + " captchaGroupId:" + captchaGroupId);
		return (String) request.getSession().getAttribute(sessionKey);
	}

	public static String getCodeAndRemove(HttpServletRequest request, String captchaGroupId) {
		String sessionKey = getSessionKey(captchaGroupId);
		// System.out.println("getCode:" + sessionKey + " captchaGroupId:" + captchaGroupId);
		String code = (String) request.getSession().getAttribute(sessionKey);
		if (code != null) {
			request.getSession().removeAttribute(sessionKey);
		}
		return code;
	}
}
