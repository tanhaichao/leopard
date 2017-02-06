package io.leopard.data4j.cache.api;

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

}
