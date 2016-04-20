package io.leopard.web4j.nobug.xss;

import java.util.Map;

public interface ParameterParser {
	Map<String, String[]> getParameterMap();

	boolean isGetMethod(String name);
}
