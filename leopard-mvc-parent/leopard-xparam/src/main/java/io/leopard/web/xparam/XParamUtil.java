package io.leopard.web.xparam;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

public class XParamUtil {

	public static boolean isFalse(String value) {
		return CustomBooleanEditor.VALUE_FALSE.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_NO.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_0.equals(value)//
				|| CustomBooleanEditor.VALUE_OFF.equalsIgnoreCase(value);
	}

	public static boolean isTrue(String value) {
		return CustomBooleanEditor.VALUE_TRUE.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_YES.equalsIgnoreCase(value)//
				|| CustomBooleanEditor.VALUE_1.equals(value)//
				|| CustomBooleanEditor.VALUE_ON.equalsIgnoreCase(value);
	}

	public static int toInt(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取cookie的值</br>
	 * 
	 * @param name cookie名称
	 * @param request http请求
	 * @return cookie值
	 */
	public static String getCookie(String name, HttpServletRequest request) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("cookie名称不能为空.");
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (name.equalsIgnoreCase(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	public static String getProxyIp(HttpServletRequest request) {
		String proxyIp = request.getHeader("X-Real-IP");
		if (proxyIp == null) {
			proxyIp = request.getHeader("RealIP");
		}
		if (proxyIp == null) {
			proxyIp = request.getRemoteAddr();
		}
		return proxyIp;
	}

	public static String[] getParameterNames(MethodParameter parameter) {
		Method method = parameter.getMethod();
		// ParameterNameDiscoverer parameterNameDiscoverer = null;
		ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		return parameterNameDiscoverer.getParameterNames(method);
	}
}
