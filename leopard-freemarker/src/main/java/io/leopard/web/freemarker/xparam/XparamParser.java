package io.leopard.web.freemarker.xparam;

import javax.servlet.http.HttpServletRequest;

public interface XparamParser {

	Boolean parse(Object[] args, Class<?>[] types, String[] names, HttpServletRequest request);
}
