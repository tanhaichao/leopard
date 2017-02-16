package io.leopard.web.mvc.option;

/**
 * 枚举信息模型(在Controller中使用)
 * 
 * @author 谭海潮
 *
 */
public class Option {

	private Object key;

	private String desc;

	public Option() {

	}

	public Option(Object key, String desc) {
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

}
