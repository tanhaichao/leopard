package io.leopard.security.admin.menu;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 子菜单
 * 
 * @author 谭海潮
 *
 */
public class Submenu {

	private String text;

	private String sref;

	private Map<String, Object> params;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSref() {
		return sref;
	}

	public void setSref(String sref) {
		this.sref = sref;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void addParam(String name, Object value) {
		if (params == null) {
			this.params = new LinkedHashMap<>();
		}
		params.put(name, value);
	}
}
