package io.leopard.web4j.nobug.xss;

import java.util.HashMap;
import java.util.Map;

public class ParameterParserQueryStringImpl implements ParameterParser {

	protected Map<String, String[]> params = new HashMap<String, String[]>();
	protected Map<String, Boolean> method = new HashMap<String, Boolean>();

	public ParameterParserQueryStringImpl(String queryString) {
		this.parseQueryString(queryString);
	}

	protected void addGetParameter(String name, String value) {
		String[] values = this.params.get(name);
		if (values == null) {
			this.addParameter(name, new String[] { value }, true);
		}
		else {
			values = append(values, value);
			this.addParameter(name, values, true);
		}
	}

	public static String[] append(String[] args, String value) {
		String[] args2 = new String[args.length + 1];
		for (int i = 0; i < args.length; i++) {
			args2[i] = args[i];
		}
		args2[args2.length - 1] = value;
		return args2;
	}

	// protected void addPostParameter(String name, String[] values) {
	// if (this.isGetMethod(name)) {
	// throw new IllegalArgumentException("参数[" + name + "]已经使用get方法提交，怎么post方法里还有?");
	// }
	// this.addParameter(name, values, false);
	// }

	protected void addParameter(String name, String[] values, boolean isGetMethod) {
		params.put(name, values);
		method.put(name, isGetMethod);
	}

	protected void parseQueryString(String queryString) {
		if (queryString == null) {
			return;
		}
		String[] params = queryString.split("&");
		for (String param : params) {
			int index = param.indexOf('=');
			String name;
			String value;
			if (index == -1) {
				name = param;
				value = null;
			}
			else {
				name = param.substring(0, index);
				value = param.substring(index + 1);
			}
			this.addGetParameter(name, value);
		}
	}

	@Override
	public boolean isGetMethod(String name) {
		Boolean method = this.method.get(name);
		if (method != null && method == true) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return this.params;
	}

}
