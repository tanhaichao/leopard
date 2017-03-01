package io.leopard.web.xparam.resolver;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
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
@Component // 之前为什么把这个注解去掉?
public class PrimitiveMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		RequestParam ann = parameter.getParameterAnnotation(RequestParam.class);
		if (ann != null) {
			return false;
		}
		// logger.info("supportsParameter name:" + parameter.getParameterName() + " clazz:" + parameter.getParameterType());

		Class<?> clazz = parameter.getParameterType();
		if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return true;
		}
		else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return true;
		}
		else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return true;
		}
		else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			return true;
		}
		else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
			return true;
		}
		else if (clazz.equals(Date.class)) {
			return true;
		}
		else if (clazz.equals(String.class)) {
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
		// if (UnderlineHandlerMethodArgumentResolver.isEnable()) {
		// }
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Object value = RequestBodyParser.getParameter(request, name);
		if (value == null) {
			value = this.getDefaultValue(parameter);
		}
		// logger.info("resolveName name:" + name + " clazz:" + parameter.getParameterType());

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
		else if (clazz.equals(boolean.class)) {
			return false;
		}
		else if (clazz.equals(String.class)) {
			return null;
		}
		else if (clazz.equals(Boolean.class)) {
			return null;
		}
		else if (clazz.equals(Integer.class)) {
			return null;
		}
		else if (clazz.equals(Long.class)) {
			return null;
		}
		else if (clazz.equals(Float.class)) {
			return null;
		}
		else if (clazz.equals(Double.class)) {
			return null;
		}
		else if (clazz.equals(Date.class)) {
			return null;
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
