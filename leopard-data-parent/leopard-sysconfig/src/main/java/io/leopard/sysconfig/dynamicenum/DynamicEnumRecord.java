package io.leopard.sysconfig.dynamicenum;

import java.util.List;

public class DynamicEnumRecord {

	private String enumId;

	private List<String> constantList;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public List<String> getConstantList() {
		return constantList;
	}

	public void setConstantList(List<String> constantList) {
		this.constantList = constantList;
	}

}
