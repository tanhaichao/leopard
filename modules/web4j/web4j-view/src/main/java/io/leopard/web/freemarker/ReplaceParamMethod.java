package io.leopard.web.freemarker;

import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class ReplaceParamMethod implements TemplateMethodModelEx {

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		String param = args.get(0).toString();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		// HttpServletRequest request = RequestUtil.getCurrentRequest();
		return this.replaceParam(param, request);
	}

	private static final Pattern pattern = Pattern.compile("([a-zA-Z0-9_]+)=([a-zA-Z0-9_]+)");

	protected String replaceParam(String param, HttpServletRequest request) {
		// String queryString = request.getQueryString();
		// if (StringUtils.isEmpty(queryString)) {
		// return "?" + param;
		// }
		Matcher m = pattern.matcher(param);
		if (!m.find()) {
			throw new IllegalArgumentException("非法参数[" + param + "].");
		}
		String key = m.group(1);
		String value = m.group(2);
		return this.getQueryString(request, key, value);
	}

	protected String getQueryString(HttpServletRequest request, String key, String value) {
		// url = url.substring(0, url.indexOf("?") == -1 ? url.length() : url.indexOf("?"));
		StringBuilder sb = new StringBuilder();
		Enumeration<String> e = request.getParameterNames();

		boolean hasReplace = false;
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value2;
			if (name.equals(key)) {
				value2 = value;
				hasReplace = true;
			}
			else {
				value2 = request.getParameter(name);
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(name).append("=").append(value2);
		}
		if (!hasReplace) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(value);
		}
		return "?" + sb.toString();
	}
}