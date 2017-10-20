package io.leopard.lang.inum;

/**
 * 支持位运算的枚举类
 * 
 * @author 谭海潮
 *
 */
public interface BitInum extends Inum {

	/**
	 * 获取字段值
	 * 
	 * @return
	 */
	int getValue();

	/**
	 * 设置字段值
	 * 
	 * @param value
	 */
	void setValue(int value);
}
