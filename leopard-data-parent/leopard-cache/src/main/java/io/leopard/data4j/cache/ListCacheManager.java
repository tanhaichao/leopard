package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IGet;
import io.leopard.data4j.cache.api.IRemoveListCache;

/**
 * 列表缓存管理.
 * 
 * @author 阿海
 * 
 */
public class ListCacheManager {
	private static final ThreadLocal<Object> CURRENT_MODEL = new ThreadLocal<Object>();

	/**
	 * 将mysql中的记录通过ThreadLocal传递给removeListCache方法.
	 * 
	 * @param model
	 */
	public static <BEAN, KEYTYPE> void transferModel(IGet<BEAN, KEYTYPE> mysqlImpl, KEYTYPE key) {
		BEAN bean = mysqlImpl.get(key);
		CURRENT_MODEL.set(bean);
	}

	public static Object getModel() {
		return CURRENT_MODEL.get();
	}

	public static <BEAN> boolean removeListCache(IRemoveListCache<BEAN> memcachedImpl) {
		@SuppressWarnings("unchecked")
		BEAN bean = (BEAN) getModel();// 这个Model是在xxxDaoCacheImpl调用xxxDaoMysqlImpl.get()方法获取到的对象
		return memcachedImpl.removeListCache(bean);
	}
}
