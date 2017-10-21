package io.leopard.exporter;

/**
 * ID转换器
 * 
 * @author 谭海潮
 *
 */
public interface IdTransverter {

	/**
	 * ID转换
	 * 
	 * @param id 旧ID
	 * @return 新ID
	 */
	String transform(String id);
}
