package io.leopard.web.mvc.option;

import java.util.Map;

public class OptionInfo {

	private String id;
	
	
	private String className;
	
	private Class<?> clazz;
	
	
	private Map<Object,Object> data;


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


	public Map<Object, Object> getData() {
		return data;
	}


	public void setData(Map<Object, Object> data) {
		this.data = data;
	}
	
	
	
}
