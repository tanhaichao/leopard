package io.leopard.data4j.cache.api;

/**
 * 从缓存中移除数据.
 * 
 * 注意:remove接口和delete接口的区别，remove只是移除缓存中的数据(没有mysql实现)，delete是删除记录(包含删除mysql中的记录);
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            IGet接口返回值类型:如User、Article、Comment等
 * 
 * @param <KEYTYPE>
 *            主键类型:如Integer、Long、String等
 */
@Deprecated
public interface IRemove<BEAN, KEYTYPE> extends IGet<BEAN, KEYTYPE> {

	/**
	 * 从缓存中移除数据.
	 * 
	 * 注意:remove接口和delete接口的区别，remove只是移除缓存中的数据(没有mysql实现)，delete是删除记录(包含删除mysql中的记录);
	 * 
	 * @param key
	 *            主键
	 * @return 移除缓存数据成功返回true，没有对应的缓存数据返回false，出错抛异常.
	 */
	boolean remove(KEYTYPE key);
}
