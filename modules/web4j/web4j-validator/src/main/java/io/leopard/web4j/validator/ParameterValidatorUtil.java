package io.leopard.web4j.validator;

import io.leopard.web4j.nobug.xss.XssUtil;

import java.util.HashMap;
import java.util.Map;

public class ParameterValidatorUtil {

	private static final Map<String, ParameterValidator> data = new HashMap<String, ParameterValidator>();

	public static void registerParameterValidator(ParameterValidator validator) {
		// System.err.println("registerParameterValidator:" + validator.getClass().getName());
		data.put(validator.getKey(), validator);
		XssUtil.addIgnoreName(validator.getKey());
	}

	public static ParameterValidator getValidator(String name) {
		return data.get(name);
	}
}
