package io.leopard.test.xarg;

/**
 * 测试参数默认值.
 * 
 * @author 谭海潮
 *
 */
public interface Xarg {

	String getKey();

	Object getValue(Class<?> type, Object value);

}
