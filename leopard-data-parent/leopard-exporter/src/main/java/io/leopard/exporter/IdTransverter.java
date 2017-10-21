package io.leopard.exporter;

/**
 * ID转换器
 * 
 * @author 谭海潮
 *
 */
public interface IdTransverter {

	boolean add(String tableName, String id, String newId);

	/**
	 * ID转换
	 * 
	 * @param tableName 表名
	 * @param id 旧ID
	 * @return 新ID
	 */
	String transform(String tableName, String id);

	/**
	 * 生成新ID
	 * 
	 * @param tableName 表名
	 * @param id 旧ID
	 * @return
	 */
	String generateNewId(String tableName, String id);

}
