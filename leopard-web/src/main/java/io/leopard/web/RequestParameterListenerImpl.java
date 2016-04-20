package io.leopard.web;

import io.leopard.web.session.RequestParameterListener;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterListenerImpl implements RequestParameterListener {

	@Override
	public void parameterGet(HttpServletRequest request, String name, String[] values) {
		// System.err.println("parameterGet name:" + name + " values:" + values);
	}

}
