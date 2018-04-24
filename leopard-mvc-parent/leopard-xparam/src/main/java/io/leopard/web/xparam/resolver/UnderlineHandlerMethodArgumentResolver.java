package io.leopard.web.xparam.resolver;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 下划线参数名称解析.
 * 
 * @author 阿海
 *
 */
// @Component
@Deprecated
public class UnderlineHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		logger.info("supportsParameter name:" + parameter.getParameterName() + " clazz:" + parameter.getParameterType());
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
		// HttpServletRequest req = (HttpServletRequest) request.getNativeRequest();
		// String underlineName = camelToUnderline(name);
		// // logger.info("resolveName name:" + name + " underlineName:" + underlineName);
		// String value = RequestBodyParser.getParameter(req, underlineName);
		// return value;
		return null;
	}

	// public static String getParameter(HttpServletRequest request, String name) {
	//// String value = RequestBodyParser.getParameter(request, name);
	//// if (enable && value == null) {
	//// value = RequestBodyParser.getParameter(request, camelToUnderline(name));
	//// }
	//// return value;
	// }

}
