package io.leopard.sysconfig.viewer;

/**
 * 属性
 * 
 * @author 谭海潮
 *
 */
public class FieldVO {

	/**
	 * 配置ID
	 */
	private String sysconfigId;

	private Object value;

	public String getSysconfigId() {
		return sysconfigId;
	}

	public void setSysconfigId(String sysconfigId) {
		this.sysconfigId = sysconfigId;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
