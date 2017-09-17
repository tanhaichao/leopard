package io.leopard.web.mvc.option;

import java.util.List;

public class OptionInfo {

	private String id;

	private String className;

	private Class<?> clazz;

	private List<OptionVO> data;

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

	public List<OptionVO> getData() {
		return data;
	}

	public void setData(List<OptionVO> data) {
		this.data = data;
	}

}
