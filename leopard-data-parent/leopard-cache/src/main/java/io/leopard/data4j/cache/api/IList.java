package io.leopard.data4j.cache.api;

import java.util.List;

/**
 * 按keyList批量查询多条记录(不包含已被标记删除的记录).
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 */
public interface IList<BEAN, KEYTYPE> extends IMap<BEAN, KEYTYPE> {
	/**
	 * 按keyList批量查询多条记录(不包含已被标记删除的记录).
	 * 
	 * @param keyList
	 *            主键列表
	 * @return 按主键列表顺序返回对象列表，返回值的记录条数和keyList的条数一致。如果某个key的对象不存在，则对应的返回值元素为null。
	 */
	List<BEAN> list(List<KEYTYPE> keyList);
}
