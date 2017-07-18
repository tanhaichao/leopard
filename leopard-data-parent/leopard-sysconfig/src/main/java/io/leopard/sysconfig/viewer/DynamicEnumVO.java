package io.leopard.sysconfig.viewer;

import java.util.List;

import io.leopard.lang.inum.daynamic.EnumConstant;

public class DynamicEnumVO {

	/**
	 * 枚举ID
	 */
	private String enumId;

	/**
	 * 元素列表
	 */
	private List<EnumConstant> constantList;

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public List<EnumConstant> getConstantList() {
		return constantList;
	}

	public void setConstantList(List<EnumConstant> constantList) {
		this.constantList = constantList;
	}

}
