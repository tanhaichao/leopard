package io.leopard.memcache;

import java.util.Date;
import java.util.List;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Memcache接口默认实现(基于Memcache的的实现).
 * 
 * @author 阿海
 * 
 */
public class MemcacheImpl implements Memcache {
	// protected Log logger = LogFactory.getLog(this.getClass());
	private MemCachedClient mcc;
	private String server = null;// { "session.labour.duowan.com:11211" };
	// private String serverList = null;
	private int maxConn = 250;
	private boolean sanitizeKeys = true;// 默认对key进行url编码

	public void setServer(String server) {
		// logger.info("server:" + server);
		this.server = server;
	}

	// public void setServers(String[] servers) {
	// this.serverList = StringUtils.join(servers, ", ");
	// logger.info("servers:" + serverList);
	// this.servers = servers;
	// }
	//
	// public void setServers(String servers) {
	// logger.info("servers:" + StringUtils.join(this.servers, ", "));
	// if (!StringUtils.isBlank(servers)) {
	// this.servers = servers.split(",");
	// }
	// }

	public String getServer() {
		return server;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public void setSanitizeKeys(boolean sanitizeKeys) {
		this.sanitizeKeys = sanitizeKeys;
	}

	public boolean isSanitizeKeys() {
		return sanitizeKeys;
	}

	public MemcacheImpl() {
	}

	public void init() {
		// logger.info("init, server:" + server);
		// logger.info("init");
	}

	public void destroy() {
		// logger.info("destroy");
	}

	public MemCachedClient getMcc() {
		if (mcc == null) {
			mcc = initMcc();
		}

		return mcc;
	}

	protected void checkKey(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("参数key不能为空.");
		}
	}

	protected synchronized MemCachedClient initMcc() {
		if (mcc != null) {
			return mcc;
		}
		String[] servers = new String[] { server };
		return initMemCachedClient(servers, maxConn, sanitizeKeys);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#incr(String )
	 */
	public long incr(String key) {
		this.checkKey(key);
		return this.getMcc().incr(key);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#addOrIncr(String, long)
	 */
	public long addOrIncr(String key, long num) {
		this.checkKey(key);
		return this.getMcc().addOrIncr(key, num);
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, String)
	 */
	public boolean put(String key, String value) {
		this.checkKey(key);
		// logger.info("servers:" + StringUtils.join(servers));
		// logger.info("put key:" + key + " value:" + value);
		// long time = System.currentTimeMillis();
		boolean success = this.getMcc().set(key, value);
		// if (logger.isDebugEnabled()) {
		// String value2 = this.get(key);
		// // logger.debug("put key:" + key + " time:" + time + " success:" + success + " value:" + value);
		// // logger.debug("get key:" + key + " time:" + time + " success:" + success + " value:" + value2);
		// }
		// if (!success) {
		// logger.error("key:" + key + " success:" + success + " value:" + value);
		// }
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, int)
	 */
	public boolean put(String key, int value) {
		this.checkKey(key);
		boolean success = this.getMcc().set(key, value);
		// if (!success) {
		// logger.error("key:" + key + " success:" + success + " value:" + value);
		// }
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, String, int)
	 */
	public boolean put(String key, String value, int seconds) {
		this.checkKey(key);
		boolean success;
		if (seconds > 0) {
			Date date = new Date();
			date.setTime(seconds * 1000);
			success = this.getMcc().set(key, value, date);
		}
		else {
			success = this.getMcc().set(key, value);
		}
		// if (!success) {
		// logger.error("put2 key:" + key + " success:" + success + " server:" + server + " value:" + value);
		// }
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#put(String, int, int)
	 */
	public boolean put(String key, int value, int seconds) {
		this.checkKey(key);
		boolean success;
		if (seconds > 0) {
			Date date = new Date();
			date.setTime(seconds * 1000);
			success = this.getMcc().set(key, value, date);
		}
		else {
			success = this.getMcc().set(key, value);
		}
		// if (!success) {
		// logger.error("put2 key:" + key + " success:" + success + " server:" + server + " value:" + value);
		// }
		return success;
	}

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#get(String, Class<BEAN>)
	// */
	// public <BEAN> BEAN get(String key, Class<BEAN> clazz) {
	// String json = this.get(key);
	// return Json.toObject(json, clazz);
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#get(String)
	 */
	public String get(String key) {
		this.checkKey(key);
		String value = (String) this.getMcc().get(key);
		// if (logger.isDebugEnabled()) {
		// logger.debug("get key:" + key + " value:" + value);
		// }
		return value;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#getInt(String)
	 */
	public int getInt(String key) {
		this.checkKey(key);
		Integer num = (Integer) this.getMcc().get(key);
		if (num == null) {
			return -1;
		}
		else {
			return num;
		}
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#remove(String ) 
	 */
	public boolean remove(String key) {
		this.checkKey(key);
		boolean success = this.getMcc().delete(key);
		// if (logger.isDebugEnabled()) {
		// logger.debug("remove key:" + key + " success:" + success);
		// }
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#flushAll()
	 */
	public boolean flushAll() {
		return this.getMcc().flushAll();
	}

	// @Override
	// public String[] getServers() {
	// return servers;
	// }

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#getObject(String)
	// */
	// public Object getObject(String key) {
	// this.checkKey(key);
	// Object obj = this.getMcc().get(key);
	// if (logger.isDebugEnabled()) {
	// logger.debug("get key:" + key + " value:" + obj);
	// }
	// return obj;
	// }

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#putObject(String, Object)
	// */
	// public boolean putObject(String key, Object obj) {
	// this.checkKey(key);
	// long time = System.currentTimeMillis();
	// boolean success = this.getMcc().set(key, obj);
	// if (logger.isDebugEnabled()) {
	// Object obj2 = this.getObject(key);
	// logger.debug("put key:" + key + " time:" + time + " success:" + success +
	// " value:" + obj);
	// logger.debug("get key:" + key + " time:" + time + " success:" + success +
	// " value:" + obj2);
	// }
	// if (!success) {
	// logger.error("key:" + key + " success:" + success + " value:" + obj);
	// }
	// return success;
	// }

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#putObject(String, Object, int)
	// */
	// public boolean putObject(String key, Object obj, int seconds) {
	// this.checkKey(key);
	// boolean success;
	// long time = System.currentTimeMillis();
	// if (seconds > 0) {
	// Date date = new Date();
	// date.setTime(seconds * 1000);
	// success = this.getMcc().set(key, obj, date);
	// }
	// else {
	// success = this.getMcc().set(key, obj);
	// }
	// if (logger.isDebugEnabled()) {
	// Object obj2 = this.getObject(key);
	// logger.debug("put key:" + key + " time:" + time + " success:" + success +
	// " value:" + obj);
	// logger.debug("get key:" + key + " time:" + time + " success:" + success +
	// " value:" + obj2);
	// }
	// if (!success) {
	// logger.error("key:" + key + " success:" + success + " value:" + obj);
	// }
	// return success;
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#mget(String[])
	 */
	public List<String> mget(String[] keys) {
		throw new UnsupportedOperationException("Not Implemented.");
	}

	// @Override
	// /**
	// * @see io.leopard.data.memcache.Memcache#mget(String[], Class<BEAN>)
	// */
	// public <BEAN> List<BEAN> mget(String[] keys, Class<BEAN> clazz) {
	// throw new NotImplementedException();
	// }

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#add(String, String)
	 */
	public boolean add(String key, String value) {
		this.checkKey(key);
		boolean success = this.getMcc().add(key, value);
		// if (!success) {
		// logger.error("key:" + key + " success:" + success + " value:" + value);
		// }
		return success;
	}

	@Override
	/**
	 * @see io.leopard.data.memcache.Memcache#add(String, String, int)
	 */
	public boolean add(String key, String value, int seconds) {
		this.checkKey(key);
		boolean success;
		if (seconds > 0) {
			Date date = new Date();
			date.setTime(seconds * 1000);
			success = this.getMcc().add(key, value, date);
		}
		else {
			success = this.getMcc().add(key, value);
		}
		// if (!success) {
		// logger.error("put2 key:" + key + " success:" + success + " server:" + server + " value:" + value);
		// }
		return success;
	}

	private MemCachedClient initMemCachedClient(String[] servers, int maxConn, boolean sanitizeKeys) {
		// logger.info("maxConn:" + maxConn + " sanitizeKeys:" + sanitizeKeys + " servers" + StringUtils.join(servers));
		Integer[] weights = new Integer[servers.length];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = 3;
		}
		// { 3 };
		// grab an instance of our connection pool
		// String server = StringUtils.join(servers);
		// int hasCode = server.hashCode();
		String poolName = "pool" + servers.hashCode();
		// logger.info("init poolName:" + poolName + " server:" + server);
		SockIOPool pool = SockIOPool.getInstance(poolName);

		MemCachedClient mcc = new MemCachedClient(poolName);

		// mcc.setErrorHandler(new ErrorHandlerImpl());
		// set the servers and the weights
		pool.setServers(servers);
		pool.setWeights(weights);

		// set some basic pool settings
		// 5 initial, 5 min, and 250 max conns
		// and set the max idle time for a conn
		// to 6 hours
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(maxConn);
		pool.setMaxIdle(1000 * 60 * 60 * 6);

		// set the sleep for the maint thread
		// it will wake up every x seconds and
		// maintain the pool size
		pool.setMaintSleep(30 * 1000);

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		// initialize the connection pool
		pool.initialize();

		mcc.setSanitizeKeys(sanitizeKeys);// 不要对key进行url编码
		// logger.info("connected poolName:" + poolName + " server:" + server + " maxConn:" + maxConn);
		// lets set some compression on for the client
		// compress anything larger than 64k
		// mcc.setCompressEnable(true);
		// mcc.setCompressThreshold(64 * 1024);
		return mcc;
	}
}
