package io.leopard.web.xparam.resolver;

import javax.servlet.ServletException;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

public abstract class AbstractNamedValueMethodArgumentResolver extends org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver {
	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		RequestParam annotation = parameter.getParameterAnnotation(RequestParam.class);
		return (annotation != null) ? new RequestParamNamedValueInfo(annotation) : new RequestParamNamedValueInfo();
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
