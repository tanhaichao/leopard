package io.leopard.web.captcha.kit;

import io.leopard.burrow.lang.inum.Snum;

/**
 * 验证码类型(手机、邮件)
 * 
 * @author 谭海潮
 *
 */
public enum CaptchaType implements Snum {

	MOBILE("mobile", "手机"), EMAIL("email", "邮件");

	private String key;
	private String desc;

	private CaptchaType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	// /**
	// * 手机验证码
	// */
	// public static String MOBILE = "mobile";
	//
	// /**
	// * 邮件验证码
	// */
	// public static String EMAIL = "email";
}
