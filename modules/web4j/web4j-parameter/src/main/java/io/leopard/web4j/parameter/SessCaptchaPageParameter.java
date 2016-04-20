package io.leopard.web4j.parameter;

import io.leopard.web4j.captcha.CaptchaView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 获取session中的验证码.
 * 
 * @author 阿海
 * 
 */
@Service
public class SessCaptchaPageParameter implements PageParameter {

	@Override
	public String getValue(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String captchaGroupId = (String) request.getAttribute("captchaGroupId");
		String sessionKey = CaptchaView.getSessionKey(captchaGroupId);
		String code = (String) session.getAttribute(sessionKey);
		if (StringUtils.isEmpty(code)) {
			throw new IllegalArgumentException("验证码未生成.");
		}
		session.removeAttribute(sessionKey);
		return code;
	}

	@Override
	public String getKey() {
		return "sessCaptcha";
	}

}
