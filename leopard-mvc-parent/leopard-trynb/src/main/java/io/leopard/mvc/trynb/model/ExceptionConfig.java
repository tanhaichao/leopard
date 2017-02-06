package io.leopard.mvc.trynb.model;

import java.util.HashMap;
import java.util.Map;

public class ExceptionConfig {

	private String type;
	private String message;
	private String statusCode;
	private String log;// false、true、info、warn、error

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		// false、true、info、warn、error
		map.put("false", "debug");
		map.put("true", "error");
		map.put("info", "info");
		map.put("warn", "warn");
		map.put("error", "error");
	}

	public static String getType(String log) {
		return map.get(log);
	}

}
