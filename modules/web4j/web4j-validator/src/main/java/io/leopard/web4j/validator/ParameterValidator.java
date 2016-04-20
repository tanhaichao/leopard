package io.leopard.web4j.validator;

/**
 * URL参数合法性检查.
 * 
 * @author 阿海
 * 
 */
public interface ParameterValidator {
	/**
	 * 参数名称(区分大小写)，在spring初始化时使用.
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * 获取参数值.
	 * 
	 * @param value
	 * @return
	 */
	void check(String value);
}
