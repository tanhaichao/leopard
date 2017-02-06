package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IGet;

public class CacheUpdater {

	public static <BEAN, KEYTYPE> boolean add(final IGet<BEAN, KEYTYPE> mysqlImpl, final IGet<BEAN, KEYTYPE> memcachedImpl, final BEAN bean) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return mysqlImpl.add(bean);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return memcachedImpl.add(bean);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static <BEAN, KEYTYPE> boolean add(final IGet<BEAN, KEYTYPE> mysqlImpl, final IGet<BEAN, KEYTYPE> memcachedImpl, final IGet<BEAN, KEYTYPE> redisImpl, final BEAN bean) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return mysqlImpl.add(bean);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return memcachedImpl.add(bean);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return redisImpl.add(bean);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	// public static <BEAN, KEYTYPE> boolean delete(final Cache<BEAN, KEYTYPE> mysqlImpl, final Cache<BEAN, KEYTYPE> memcachedImpl, final KEYTYPE key) {
	// DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
	// @Override
	// public Object execute() {
	// return mysqlImpl.del(key);
	// }
	// }, new DaoInvoker() {
	// @Override
	// public Object execute() {
	// return memcachedImpl.del(key);
	// }
	// });
	// return (Boolean) daoStatus.getMysqlStatus();
	// }

	// public static <BEAN, KEYTYPE> boolean update(IGet<BEAN, KEYTYPE> cache, KEYTYPE key, UpdateInvoker<BEAN> invoker) {
	// BEAN bean = cache.get(key);
	// if (bean == null) {
	// return false;
	// }
	// return invoker.update(bean);
	// }

}
