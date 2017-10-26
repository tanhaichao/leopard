package io.leopard.web.mvc.option;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 枚举信息模型(在Controller中使用)
 * 
 * @author 谭海潮
 *
 */
public class OptionVO {

	private Object key;

	private String desc;

	/**
	 * 子枚举列表
	 */
	@JsonInclude(Include.NON_NULL)
	private List<OptionVO> childList;

	public OptionVO() {

	}

	public OptionVO(Object key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<OptionVO> getChildList() {
		return childList;
	}

	public void setChildList(List<OptionVO> childList) {
		this.childList = childList;
	}

}
