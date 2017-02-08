package io.leopard.web.mvc.option;

import java.util.List;
import java.util.Map;

public class OptionInfo {

	private String id;

	private String className;

	private Class<?> clazz;

	private List<Option> data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<Option> getData() {
		return data;
	}

	public void setData(List<Option> data) {
		this.data = data;
	}

}
