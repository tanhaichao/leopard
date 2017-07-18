package io.leopard.sysconfig.viewer;

import java.util.Date;
import java.util.List;

public class DynamicEnumDataVO {

	/**
	 * 最后更新时间
	 */
	private Date lmodify;

	private List<DynamicEnumVO> enumList;

	public Date getLmodify() {
		return lmodify;
	}

	public void setLmodify(Date lmodify) {
		this.lmodify = lmodify;
	}

	public List<DynamicEnumVO> getEnumList() {
		return enumList;
	}

	public void setEnumList(List<DynamicEnumVO> enumList) {
		this.enumList = enumList;
	}

}
