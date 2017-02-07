package io.leopard.web.nobug.xss;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssCheckerUrlImpl implements XssChecker {
	private static XssChecker instance = new XssCheckerUrlImpl();

	public static XssChecker getInstance() {
		return instance;
	}

	private static Pattern pattern = Pattern.compile("[<>'\"]");

	@Override
	public boolean check(String value) {
		// value = StringUtils.lowerCase(value);
		if (value != null) {
			value = value.toLowerCase();
		}

		// FIXME ahai URL合法性判断未完全
		Matcher m = pattern.matcher(value);
		if (m.find()) {
			return true;
		}
		if (value.indexOf("script") != -1) {
			return true;
		}
		return false;
	}

}
