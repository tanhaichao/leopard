package io.leopard.data4j.cache.api;

/**
 * 清除列表缓存
 * 
 * @author 阿海
 * 
 * @param <BEAN>
 *            被删除的对象
 */
public interface IRemoveListCache<BEAN> {

	/**
	 * 清除列表缓存.
	 * 
	 * @param bean
	 *            当前操作的model
	 * @return
	 */
	boolean removeListCache(BEAN bean);
}
