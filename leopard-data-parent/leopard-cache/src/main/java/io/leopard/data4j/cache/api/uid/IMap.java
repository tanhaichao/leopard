package io.leopard.data4j.cache.api.uid;

import io.leopard.data4j.cache.api.ISimpleMap;

import java.util.Map;
import java.util.Set;

/**
 * 按keySet批量查询多条记录(不包含已被标记删除的记录).
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 * 
 */
public interface IMap<BEAN, KEYTYPE> extends ISimpleMap<BEAN, KEYTYPE>, IUnDelete<BEAN, KEYTYPE> {

	/**
	 * 按keySet批量查询多条记录(不包含已被标记删除的记录).
	 * 
	 * @param keySet
	 *            主键集合。注意，请使用LinkedHashSet等固定顺序的Set实现.
	 * 
	 * @return 按主键集合顺序返回对象集合(LinkedHashSet)，返回值的记录条数和keySet的条数一致。如果某个key的对象不存在，则对应的返回值元素值为null。
	 */
	Map<KEYTYPE, BEAN> map(Set<KEYTYPE> keySet);
}
