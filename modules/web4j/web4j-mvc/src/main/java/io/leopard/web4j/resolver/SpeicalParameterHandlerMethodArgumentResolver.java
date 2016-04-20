package io.leopard.web4j.resolver;

import io.leopard.web4j.parameter.PageParameter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * 页面特殊参数.
 * 
 * @author 阿海
 *
 */
public class SpeicalParameterHandlerMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	// TODO ahai 这里有必要使用线程安全的Map吗？
	private static final Map<String, PageParameter> data = new HashMap<String, PageParameter>();

	public static void registerPageParameter(PageParameter page) {
		data.put(page.getKey(), page);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		String name = parameter.getParameterName();
		boolean isSpecialName = data.containsKey(name);
		return isSpecialName;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		RequestParam annotation = parameter.getParameterAnnotation(RequestParam.class);
		return (annotation != null) ? new RequestParamNamedValueInfo(annotation) : new RequestParamNamedValueInfo();
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		PageParameter page = data.get(name);
		if (page == null) {
			throw new IllegalArgumentException("未知参数名称[" + name + "].");
		}
		return page.getValue((HttpServletRequest) request.getNativeRequest());
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter) throws ServletException {

	}

	private static class RequestParamNamedValueInfo extends NamedValueInfo {

		public RequestParamNamedValueInfo() {
			super("", false, ValueConstants.DEFAULT_NONE);
		}

		public RequestParamNamedValueInfo(RequestParam annotation) {
			super(annotation.value(), annotation.required(), annotation.defaultValue());
		}
	}

}
