package io.leopard.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户信息参数封装
 * 
 * sessUsername和proxyIp
 * 
 * @author 阿海
 * 
 */
public class LeopardRequestWrapper extends SessionRequestWrapper {
	private RequestParameterListener requestParameterListener = new RequestParameterListenerImpl();
	private RequestAttributeListener requestAttributeListener = new RequestAttributeListenerImpl();

	public LeopardRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(request, response);
	}

	// public String getSuperParameter(String name) {
	// return super.getParameter(name);
	// }

	@Override
	public String getParameter(String name) {
		String[] values = this.getParameterValues(name);
		if (values == null) {
			return null;
		}
		else {
			return values[0];
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		requestParameterListener.parameterGet((HttpServletRequest) super.getRequest(), name, values);
		// if (values != null) {
		// this.checkXxxParameter(name, values);
		// }
		//
		// ParameterValidator validator = ParameterValidatorUtil.getValidator(name);
		// if (validator != null) {
		// if (values == null) {
		// validator.check(null);
		// }
		// else {
		// validator.check(values[0]);
		// }
		// }
		return values;
	}

	// protected void checkXxxParameter(String name, String[] values) {
	// if (!XssUtil.isEnable()) {
	// return;
	// }
	//
	// for (String value : values) {
	// try {
	// XssUtil.checkParameter(name, value);
	// }
	// catch (XssException e) {
	// String uri = RequestUtil.getRequestContextUri(request);
	// boolean isNoXss = XssAttributeData.isNoXss(uri, name);
	// if (!isNoXss) {
	// throw e;
	// }
	// }
	// }
	//
	// }

	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		this.requestAttributeListener.attributeGet((HttpServletRequest) super.getRequest(), name, value);
		// boolean needCheckXss = needCheckXss();
		// if (!needCheckXss) {
		// return value;
		// }
		// try {
		// XssAttributeCheckUtil.checkAttribute(name, value);
		// return value;
		// }
		// catch (XssException e) {
		// System.err.println("attribute name:" + name + " " + e.getMessage() + " json:" + Json.toFormatJson(value));
		// throw e;
		// }
		return value;
	}

	// protected boolean needCheckXss() {
	// // TODO ahai 这里能否做缓存?
	// Boolean ignore = (Boolean) super.getAttribute(XssAttributeCheckUtil.IGNORE_XSS_ATTRIBUTE_NAME);
	// if (ignore != null && ignore) {
	// return false;
	// }
	// return true;
	// }

}
