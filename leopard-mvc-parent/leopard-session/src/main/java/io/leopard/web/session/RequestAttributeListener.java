package io.leopard.web.session;

import javax.servlet.http.HttpServletRequest;

public interface RequestAttributeListener {

	void attributeGet(HttpServletRequest request, String name, Object value);

}
