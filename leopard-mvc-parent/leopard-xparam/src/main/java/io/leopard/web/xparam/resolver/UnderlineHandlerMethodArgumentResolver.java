package io.leopard.web.xparam.resolver;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 下划线参数名称解析.
 * 
 * @author 阿海
 *
 */
@Component
public class UnderlineHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${xparam.underline}")
	private String underline;

	@PostConstruct
	public void init() {
		enable = !"false".equals(underline);
	}

	private static boolean enable = true;

	public static boolean isEnable() {
		return enable;
	}

	public static void setEnable(boolean enable) {
		UnderlineHandlerMethodArgumentResolver.enable = enable;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		String name = parameter.getParameterName();
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		// TODO 重启的时候name会为null?
		boolean supports = false;
		for (char ch : name.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				// 有大写就返回true.
				supports = true;
				break;
			}
		}
		// logger.info("supportsParameter name:" + name + " supports:" + supports);
		return supports;
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
		String underlineName = camelToUnderline(name);
		// logger.info("resolveName name:" + name + " underlineName:" + underlineName);
		String value = req.getParameter(underlineName);
		return value;
	}

	
	public static String getParameter(HttpServletRequest request, String name) {
		String value = request.getParameter(name);
		if (enable && value == null) {
			value = request.getParameter(camelToUnderline(name));
		}
		return value;
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线方式.
	 */
	public static String camelToUnderline(String param) {
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
