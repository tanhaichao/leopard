package io.leopard.jetty.handler;

import javax.servlet.http.HttpServletRequest;

public interface ResourceAppender {

	void append(HttpServletRequest request, String path, StringBuffer sb);

}
