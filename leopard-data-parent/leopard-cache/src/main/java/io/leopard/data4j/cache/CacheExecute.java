package io.leopard.data4j.cache;

import io.leopard.data4j.cache.api.IAdd;
import io.leopard.data4j.cache.api.IDelete;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CacheExecute {
	private static final Log logger = LogFactory.getLog(CacheExecute.class);

	public static <BEAN> boolean add(final IAdd<BEAN> daoMysqlImpl, final IAdd<BEAN> daoMemcachedImpl, final BEAN bean) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMysqlImpl.add(bean);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemcachedImpl.add(bean);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static <BEAN, KEYTYPE> boolean delete(final IDelete<BEAN, KEYTYPE> daoMysqlImpl, final IDelete<BEAN, KEYTYPE> daoMemcachedImpl, final KEYTYPE key, final String username, final Date lmodify) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMysqlImpl.delete(key, username, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemcachedImpl.delete(key, username, lmodify);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static <BEAN, KEYTYPE> boolean delete(final io.leopard.data4j.cache.api.uid.IDelete<BEAN, KEYTYPE> daoMysqlImpl,
			final io.leopard.data4j.cache.api.uid.IDelete<BEAN, KEYTYPE> daoMemcachedImpl, final KEYTYPE key, final long opuid, final Date lmodify) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMysqlImpl.delete(key, opuid, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemcachedImpl.delete(key, opuid, lmodify);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static <BEAN, KEYTYPE> boolean delete(final io.leopard.data4j.cache.api.uid.IDelete<BEAN, KEYTYPE> daoMysqlImpl,
			final io.leopard.data4j.cache.api.uid.IDelete<BEAN, KEYTYPE> daoMemcachedImpl, final io.leopard.data4j.cache.api.uid.IDelete<BEAN, KEYTYPE> daoMemoryImpl, final KEYTYPE key,
			final long opuid, final Date lmodify) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMysqlImpl.delete(key, opuid, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemcachedImpl.delete(key, opuid, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemoryImpl.delete(key, opuid, lmodify);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static <BEAN, KEYTYPE> boolean delete(final IDelete<BEAN, KEYTYPE> daoMysqlImpl, final IDelete<BEAN, KEYTYPE> daoMemcachedImpl, final IDelete<BEAN, KEYTYPE> daoMemoryImpl,
			final KEYTYPE key, final String username, final Date lmodify) {
		DaoStatus daoStatus = CacheExecute.execute(new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMysqlImpl.delete(key, username, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemcachedImpl.delete(key, username, lmodify);
			}
		}, new DaoInvoker() {
			@Override
			public Object execute() {
				return daoMemoryImpl.delete(key, username, lmodify);
			}
		});
		return (Boolean) daoStatus.getMysqlStatus();
	}

	public static DaoStatus execute(DaoInvoker mysqlInvoker, DaoInvoker memcachedInvoker) {
		Object mysqlStatus = null;
		Object memcachedStatus = null;
		mysqlStatus = mysqlInvoker.execute();
		try {
			memcachedStatus = memcachedInvoker.execute();
		}
		catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		DaoStatus daoStatus = new DaoStatus();
		daoStatus.setMysqlStatus(mysqlStatus);
		daoStatus.setMemcachedStatus(memcachedStatus);
		// daoStatus.setTcStatus(tcStatus);
		return daoStatus;
	}

	protected static DaoStatus execute(DaoInvoker mysqlInvoker, DaoInvoker memcachedInvoker, DaoInvoker tcInvoker) {
		Object mysqlStatus = null;
		Object memcachedStatus = null;
		Object tcStatus = null;
		mysqlStatus = mysqlInvoker.execute();
		try {
			memcachedStatus = memcachedInvoker.execute();
		}
		catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		try {
			tcStatus = tcInvoker.execute();
		}
		catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		DaoStatus daoStatus = new DaoStatus();
		daoStatus.setMysqlStatus(mysqlStatus);
		daoStatus.setMemcachedStatus(memcachedStatus);
		daoStatus.setTcStatus(tcStatus);
		return daoStatus;
	}
}
