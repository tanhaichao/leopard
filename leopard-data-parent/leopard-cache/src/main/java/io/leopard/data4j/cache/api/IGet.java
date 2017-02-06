package io.leopard.data4j.cache.api;

/**
 * 根据主键获取单条记录(不返回已标记删除的记录).
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 */
public interface IGet<BEAN, KEYTYPE> extends IAdd<BEAN> {

	/**
	 * 根据主键获取单条记录(不返回已标记删除的记录).
	 * 
	 * @param key
	 *            主键类型:如Integer、Long、String等
	 * 
	 * @return 记录存在返回对象，不存在返回null，其他情况抛异常.
	 */
	BEAN get(KEYTYPE key);

}
