package io.leopard.web.xparam.resolver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.leopard.web.xparam.PathRegex;

/**
 * 使用正则表达式解析URL作为Controller参数.
 * 
 * @author 阿海
 *
 */
@Component
public class PathRegexHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		PathRegex annot = parameter.getParameterAnnotation(PathRegex.class);
		if (annot == null) {
			return false;
		}
		System.err.println("annot.value():" + annot.value());
		if (!StringUtils.hasText(annot.value())) {
			return false;
		}
		return true;
	}

	/**
	 * Return a Map with all URI template variables or an empty map.
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		PathRegex annot = parameter.getParameterAnnotation(PathRegex.class);
		String regex = annot.value();
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String uri = request.getRequestURI();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(uri);
		if (m.find()) {
			return m.group(1);
		}
		throw new IllegalArgumentException("解析URL出错[" + regex + "].");
	}

}
