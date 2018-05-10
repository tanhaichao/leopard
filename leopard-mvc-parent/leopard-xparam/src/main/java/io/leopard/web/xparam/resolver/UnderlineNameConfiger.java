package io.leopard.web.xparam.resolver;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 下划线参数名称解析.
 * 
 * @author 阿海
 *
 */
@Component
public class UnderlineNameConfiger {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${xparam.underline}")
	private String underline;

	@PostConstruct
	public void init() {
		enable = !"false".equals(underline);
	}

	private static Boolean enable = true;

	public static boolean isEnable() {
		if (enable == null) {
			throw new RuntimeException("未初始化.");
		}
		return enable;
	}

	public static void setEnable(boolean enable) {
		UnderlineNameConfiger.enable = enable;
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线方式.
	 */
	public static String camelToUnderline(String param) {
		if (!isEnable()) {
			return param;
		}
		if (param == null || param.length() == 0) {
			return param;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
