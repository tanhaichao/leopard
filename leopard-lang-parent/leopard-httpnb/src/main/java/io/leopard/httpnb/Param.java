package io.leopard.httpnb;

public class Param {

	private String name;
	private Object value;

	private boolean key;

	public Param() {

	}

	public Param(String name, Object value) {
		this(name, value, false);
	}

	public Param(String name, Object value, boolean key) {
		this.name = name;
		this.value = value;
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "name:" + name + " value:" + value + " key:" + key;
	}

}
