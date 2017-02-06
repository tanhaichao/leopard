package io.leopard.web.captcha;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;

import io.leopard.web.xparam.XParam;

/**
 * 获取session中的验证码.
 * 
 * @author 阿海
 * 
 */
public class SessCaptchaXParam implements XParam {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public String getKey() {
		return "sessCaptcha";
	}

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		return getSessCaptcha(request, parameter);
	}

	public static String getSessCaptcha(HttpServletRequest request, MethodParameter parameter) {
		String captchaGroupId = getGroupId(parameter);
		String captcha;
		Readonly readonly = parameter.getParameterAnnotation(Readonly.class);
		if (readonly == null) {
			captcha = CaptchaUtil.getCodeAndRemove(request, captchaGroupId);
		}
		else {
			captcha = CaptchaUtil.getCode(request, captchaGroupId);
		}
		// System.err.println("captchaGroupId:" + captchaGroupId + " captcha:" + captcha);
		return captcha;
	}

	private static String getGroupId(MethodParameter parameter) {
		CaptchaGroup captchaGroup = parameter.getMethod().getAnnotation(CaptchaGroup.class);
		if (captchaGroup != null) {
			return captchaGroup.value();
		}
		return null;
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
