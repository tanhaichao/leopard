package io.leopard.web.xparam.resolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * 基本数据类型
 * 
 * @author ahai
 *
 */
public class PrimitiveMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

	public PrimitiveMethodArgumentResolver() {

	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
		if (ann != null) {
			return false;
		}

		Class<?> clazz = parameter.getParameterType();
		if (clazz.equals(long.class)) {
			return true;
		}
		else if (clazz.equals(int.class)) {
			return true;
		}
		else if (clazz.equals(double.class)) {
			return true;
		}
		else if (clazz.equals(float.class)) {
			return true;
		}
		return false;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		return new RequestParamNamedValueInfo();
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		// System.err.println("PrimitiveMethodArgumentResolver resolveName name:" + name);
		if (UnderlineHandlerMethodArgumentResolver.isEnable()) {
			name = UnderlineHandlerMethodArgumentResolver.camelToUnderline(name);
		}
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Object value = RequestBodyArgumentResolver.getParameter(request, name);
		if (value == null) {
			value = this.getDefaultValue(parameter);
		}
		return value;
	}

	protected Object getDefaultValue(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		if (clazz.equals(long.class)) {
			return 0L;
		}
		else if (clazz.equals(int.class)) {
			return 0;
		}
		else if (clazz.equals(double.class)) {
			return 0D;
		}
		else if (clazz.equals(float.class)) {
			return 0f;
		}
		throw new RuntimeException("未知类型[" + clazz.getName() + "].");
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {

	}

	//
	private static class RequestParamNamedValueInfo extends NamedValueInfo {

		public RequestParamNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public RequestParamNamedValueInfo(RequestParam annotation) {
			super(annotation.name(), annotation.required(), annotation.defaultValue());
		}
	}
}
