package io.leopard.web4j.nobug.xss;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class ParameterParserImpl extends ParameterParserQueryStringImpl implements ParameterParser {

	public ParameterParserImpl(HttpServletRequest request) {
		super(request.getQueryString());
		this.parsePost(request.getParameterMap());
	}

	protected void parsePost(Map<String, String[]> map) {
		Set<Entry<String, String[]>> set = map.entrySet();
		for (Entry<String, String[]> entry : set) {
			String name = entry.getKey();
			if (params.containsKey(name)) {
				continue;
			}
			this.addParameter(name, entry.getValue(), false);
		}
	}

}
