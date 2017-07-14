package io.leopard.sysconfig.viewer;

import java.util.Date;
import java.util.List;

public class SysconfigVO {

	/**
	 * 最后更新时间
	 */
	private Date lmodify;

	private List<FieldVO> fieldList;

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public List<FieldVO> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldVO> fieldList) {
		this.fieldList = fieldList;
	}

}
