package io.leopard.lang.inum.daynamic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnumConstant {

	private Object key;

	private String desc;

	/**
	 * 扩展参数
	 */
	private Map<String, Object> parameterMap;

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void putParameter(String parameterName, Object value) {
		if (parameterMap == null) {
			parameterMap = new ConcurrentHashMap<>();
		}
		parameterMap.put(parameterName, value);
	}

	public Object getParameter(String parameterName) {
		if (parameterMap == null) {
			return null;
		}
		return parameterMap.get(parameterName);
	}

	public Map<String, Object> getParameterMap() {
		return this.parameterMap;
	}

	@Override
	public String toString() {
		return "key:" + key + " desc:" + desc + " parameters:" + parameterMap;
	}

}
