package io.leopard.web.session;

import javax.servlet.http.HttpServletRequest;

public interface RequestParameterListener {

	void parameterGet(HttpServletRequest request, String name, String[] values);

}
