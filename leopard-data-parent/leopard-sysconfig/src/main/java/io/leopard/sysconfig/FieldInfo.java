package io.leopard.sysconfig;

import java.lang.reflect.Field;

public class FieldInfo {

	private Field field;

	private Object bean;

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

}
