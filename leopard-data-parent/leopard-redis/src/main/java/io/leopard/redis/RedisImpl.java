package io.leopard.redis;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import io.leopard.redis.util.IJedisPool;
import io.leopard.redis.util.RedisBackup;
import io.leopard.redis.util.RedisUtil;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**
 * Redis实现(单机Redis).
 * 
 * @author 阿海
 * 
 */
public class RedisImpl extends AbstractRedis implements Redis {

	private IJedisPool pool;

	protected String server;
	protected String password;

	public RedisImpl() {

	}

	public RedisImpl(String server, int maxActive, int timeout) {
		this(server, maxActive, 0, false, timeout);
	}

	public RedisImpl(String server, int maxActive, int initialPoolSize, boolean enableBackup, int timeout) {
		this(server, maxActive, initialPoolSize, enableBackup, "04:01", timeout);
	}

	/**
	 * 构造redis客户端对象.
	 * 
	 * @param server 服务器
	 * @param maxActive 连接池最大连接数
	 * @param enableBackup 是否开启备份
	 */
	public RedisImpl(String server, int maxActive, int initialPoolSize, boolean enableBackup, String backupTime, int timeout) {
		this.setServer(server);
		this.setMaxActive(maxActive);
		this.setInitialPoolSize(initialPoolSize);
		this.setEnableBackup(enableBackup);
		this.setBackupTime(backupTime);
		this.setTimeout(timeout);
	}

	public void setServer(String server) {
		this.server = server;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private interface Invoker {
		public Object execute(Jedis jedis);
	}

	@PostConstruct
	@Override
	public void init() {
		// System.err.println("RedisImpl server:" + server);
		try {
			this.pool = RedisUtil.createJedisPool(server, timeout, maxActive, password);
		}
		catch (RuntimeException e) {
			System.err.println("server:" + server + " timeout:" + timeout);
			logger.error("server:" + server + " timeout:" + timeout);
			throw e;
		}
		// this.ludis = new LudisImpl(this);

		if (enableBackup) {
			RedisBackup backup = new RedisBackup();
			backup.setBackupTime(backupTime);
			backup.run(this);
		}

		try {
			this.initPool();
		}
		catch (Exception e) {
			logger.error("初始化redis[" + this.server + "]连接数出错:" + e.getMessage());
		}
	}

	/**
	 * 初始化默认连接数量.
	 */
	protected void initPool() {
		// System.err.println("initPool server:" + this.server +
		// " initialPoolSize:" + initialPoolSize + " start");
		if (this.initialPoolSize <= 0) {
			return;
		}
		// windows环境关闭初始化默认redis连接功能?
		if (System.getProperty("os.name").startsWith("Windows")) {
			return;
		}
		int size;
		if (this.initialPoolSize > this.maxActive) {
			size = this.maxActive;
		}
		else {
			size = this.initialPoolSize;
		}

		Jedis[] jedisArr = new Jedis[size];
		for (int i = 0; i < jedisArr.length; i++) {
			jedisArr[i] = this.getResource();
		}

		// int numActive = pool.getInternalPool().getNumActive();

		for (int i = 0; i < jedisArr.length; i++) {
			this.returnResource(jedisArr[i]);
		}
		// int numActive2 = pool.getInternalPool().getNumActive();
		// System.err.println("initPool server:" + this.server +
		// " initialPoolSize:" + initialPoolSize + " numActive:" + numActive +
		// " numActive2:" + numActive2 + " end");
	}

	/**
	 * 封装错误信息.
	 * 
	 * @param e
	 * @return
	 */
	protected String getErrorMessage(Exception e) {

		String ip;
		try {
			ip = InetAddress.getByName(server.split(":")[0]).getHostAddress();
		}
		catch (UnknownHostException e1) {
			ip = null;
			// throw new RuntimeException(e1.getMessage(), e1);
		}
		String message = "server:" + server + " ip:" + ip + " messsage:" + e.getMessage();
		logger.error(message);
		return message;
	}

	@Override
	public Jedis getResource() {
		// long startTime = System.nanoTime();
		try {
			return this.pool.getResource();
		}
		// ahai 20131026 新版redis连接池的异常信息已经包含了IP和端口信息.
		catch (JedisConnectionException e) {
			String message = this.getErrorMessage(e);
			throw new JedisConnectionException(message, e);
			// throw e;
		}
		// finally {
		// long endTime = System.nanoTime();
		// long time = (endTime - startTime) / 1000L / 1000L; // time 单位:毫秒
		// if (time >= 10) {
		// String message = "server:" + server;
		// message += " time:" + time;
		// DataSourceLog.debug("getResource", message);
		// }
		// if (time >= 50) {
		// this.getResourceSlowLog(time);
		// }
		// METHOD_TIME.addByStartTime("io.leopard.data.redis.RedisImpl.getResource",
		// startTime);
		// }
	}

	// private int slowCount = 0;

	/**
	 * Redis连接慢日志.
	 * 
	 * @param time
	 */
	protected void getResourceSlowLog(long time) {
		// slowCount++;
		// if (slowCount > 10) {
		// AlarmSenderImpl.getInstance().send("连接Redis太慢,server:" + server +
		// " time[" + time + "]");
		// }
	}

	@Override
	public void returnResource(Jedis jedis) {
		this.pool.returnResource(jedis);
	}

	@Override
	public Long del(final String... keys) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.del(keys);
			}
		});
	}

	@Override
	public Long del(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	@Override
	public Long append(final String key, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.append(key, value);
			}
		});
	}

	@Override
	public boolean append(final String key, final String value, final int seconds) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				transaction.append(key, value);
				transaction.expire(key, seconds);
				transaction.exec();
				return true;
			}
		});
	}

	@Override
	public boolean append(final List<String> keyList, final List<String> valueList, final int seconds) {
		RedisUtil.checkList(keyList, valueList);

		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				for (int i = 0; i < keyList.size(); i++) {
					transaction.append(keyList.get(i), valueList.get(i));
					transaction.expire(keyList.get(i), seconds);
				}
				transaction.exec();
				return true;
			}
		});
	}

	@Override
	public String set(final String key, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.set(key, value);
			}
		});

	}

	@Override
	public boolean set(final List<String> keyList, final List<String> valueList) {
		RedisUtil.checkList(keyList, valueList);

		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Transaction transaction = jedis.multi();
				for (int i = 0; i < keyList.size(); i++) {
					transaction.set(keyList.get(i), valueList.get(i));
				}
				transaction.exec();
				return true;
			}
		});

	}

	@Override
	public Long expire(final String key, final int seconds) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	public Long ttl(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	@Override
	public String set(final String key, final String value, final int seconds) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	public boolean rename(final String oldkey, final String newkey) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				String result = jedis.rename(oldkey, newkey);
				return "OK".equalsIgnoreCase(result);
			}
		});

	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		boolean success = this.rename(oldkey, newkey);
		return success;
	}

	@Override
	public RedisInfo info() {
		String info = (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.info();
			}
		});

		RedisInfo redisInfo = new RedisInfo(info);
		return redisInfo;
	}

	@Override
	public long getUsedMemory() {
		RedisInfo redisInfo = this.info();
		long usedMemory = redisInfo.getUsedMemory();
		return usedMemory;
	}

	@Override
	public long dbSize() {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.dbSize();
			}
		});
	}

	@Override
	public boolean flushAll() {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.flushAll();
				return true;
			}
		});
	}

	@Override
	public boolean flushDB() {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.flushDB();
				return true;
			}
		});

	}

	@Override
	public Long incr(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	@Override
	public String get(final String key) {
		// System.out.println("redisImpl get:" + key);
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				// System.out.println("jedis:" + jedis);
				return jedis.get(key);
			}
		});
	}

	protected void returnBrokenResource(Jedis jedis) {
		this.pool.returnBrokenResource(jedis);
	}

	/**
	 * 执行jedis的操作.
	 * 
	 * @param invoker 调度接口
	 * @return
	 */
	protected Object execute(Invoker invoker) {
		Jedis jedis = this.getResource();
		try {
			return invoker.execute(jedis);
		}
		catch (JedisConnectionException e) {
			this.returnBrokenResource(jedis);
			String message = this.getErrorMessage(e);
			// message += " key:" + key;
			throw new JedisConnectionException(message, e);
		}
		catch (RuntimeException e) {
			this.returnBrokenResource(jedis);
			throw e;
		}
		catch (Exception e) {
			this.returnBrokenResource(jedis);
			throw new RuntimeException(e.getMessage(), e);
		}
		finally {
			// jedis.close();
			this.returnResource(jedis);
		}
	}

	@Override
	public String getSet(final String key, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getSet(key, value);
			}
		});
	}

	@Override
	public Long zcard(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcard(key);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrevrange(final String key, final long start, final long end) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrange(final String key, final long start, final long end) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
	}

	@Override
	public Long zadd(final String key, final double score, final long member) {
		return this.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zadd(final String key, final double score, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zadd(key, score, member);
			}
		});
	}

	@Override
	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}

	@Override
	public Long setrange(final String key, final long offset, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setrange(key, offset, value);
			}
		});
	}

	@Override
	public Transaction multi() {
		Jedis jedis = this.getResource();
		return jedis.multi();
	}

	@Override
	public Long srem(final String key, final String... member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srem(key, member);
			}
		});
	}

	@Override
	public Boolean exists(final String key) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	@Override
	public String type(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.type(key);
			}
		});
	}

	@Override
	public Long expireAt(final String key, final long unixTime) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.expireAt(key, unixTime);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final boolean value) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Boolean getbit(final String key, final long offset) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getbit(key, offset);
			}
		});
	}

	@Override
	public String getrange(final String key, final long startOffset, final long endOffset) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.getrange(key, startOffset, endOffset);
			}
		});
	}

	@Override
	public Long setnx(final String key, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setnx(key, value);
			}
		});
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	public Long decrBy(final String key, final long integer) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.decrBy(key, integer);
			}
		});
	}

	@Override
	public Long decr(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	@Override
	public Long incrBy(final String key, final long integer) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
	}

	@Override
	public String substr(final String key, final int start, final int end) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.substr(key, start, end);
			}
		});
	}

	@Override
	public Long hset(String key, long field, String value) {
		return this.hset(key, Long.toString(field), value);
	}

	@Override
	public Long hset(final String key, final String field, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	@Override
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	@Override
	public String hget(final String key, final String field) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hsetnx(String, String, String)
	 */
	public Long hsetnx(final String key, final String field, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hsetnx(key, field, value);
			}
		});
	}

	@Override
	public String hmset(final String key, final Map<String, String> hash) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> mget(final String... keys) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> hmget(final String key, final String... fields) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
	}

	@Override
	public Long hincrBy(final String key, final String field, final long value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hexists(String, String)
	 */
	public Boolean hexists(final String key, final String field) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hexists(key, field);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hexists(key, field);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	// @Override
	// /**
	// * @see io.leopard.data.redis.Redis#hdel(String, int)
	// */
	// public Long hdel(String key, int field) {
	// return this.hdel(key, Integer.toString(field));
	// }

	@Override
	/**
	 * @see io.leopard.data.redis.Redis#hdel(String, int)
	 */
	public Long hdel(String key, long field) {
		return this.hdel(key, Long.toString(field));
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hdel(String, String...)
	 */
	public Long hdel(final String key, final String... field) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hdel(key, field);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hdel(key, field);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hlen(String)
	 */
	public Long hlen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hlen(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hlen(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hkeys(String)
	 */
	public Set<String> hkeys(final String key) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hkeys(key);
			}
		});

		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hkeys(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hvals(String)
	 */
	public List<String> hvals(final String key) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hvals(key);
			}
		});
		// Jedis jedis = this.getResource();
		// try {
		// return jedis.hvals(key);
		// }
		// catch (RuntimeException e) {
		// this.pool.returnBrokenResource(jedis);
		// throw e;
		// }
		// catch (Exception e) {
		// this.pool.returnBrokenResource(jedis);
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// finally {
		// this.pool.returnResource(jedis);
		// }
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#hgetAll(String)
	 */
	public Map<String, String> hgetAll(final String key) {
		return (Map<String, String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	@Override
	/**
	 * @see io.leopard.data.redis.JedisCommands#rpush(String, String...)
	 */
	public Long rpush(final String key, final String... strings) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpush(key, strings);
			}
		});
	}

	@Override
	public Long lpush(final String key, final String... strings) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpush(key, strings);
			}
		});
	}

	@Override
	public Long llen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	@Override
	public String ltrim(final String key, final long start, final long end) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.ltrim(key, start, end);
			}
		});
	}

	@Override
	public String lindex(final String key, final long index) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lindex(key, index);
			}
		});
	}

	@Override
	public String lset(final String key, final long index, final String value) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lset(key, index, value);
			}
		});
	}

	@Override
	public Long lrem(final String key, final long count, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
	}

	@Override
	public String lpop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpop(key);
			}
		});
	}

	@Override
	public String rpop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	@Override
	public Long sadd(final String key, final String... members) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sadd(key, members);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> smembers(final String key) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.smembers(key);
			}
		});
	}

	@Override
	public String spop(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.spop(key);
			}
		});
	}

	@Override
	public Long scard(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.scard(key);
			}
		});
	}

	@Override
	public Boolean sismember(final String key, final String member) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> srandmember(final String key, final int count) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srandmember(key, count);
			}
		});
	}

	@Override
	public String srandmember(final String key) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.srandmember(key);
			}
		});
	}

	@Override

	public Long zrem(String key, long member) {
		return this.zrem(key, Long.toString(member));
	}

	@Override

	public Long zrem(final String key, final String... members) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrem(key, members);
			}
		});
	}

	@Override
	public Double zincrby(final String key, final double score, final long member) {
		return this.zincrby(key, score, Long.toString(member));
	}

	@Override
	public Double zincrby(final String key, final double score, final String member) {
		return (Double) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zincrby(key, score, member);
			}
		});
	}

	@Override
	public Long zrank(final String key, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrank(key, member);
			}
		});
	}

	@Override
	public Long zrevrank(final String key, final String member) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrank(key, member);
			}
		});
	}

	@Override
	public Long zinterstore(final String dstkey, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				// System.out.println("sets:" + StringUtils.join(sets, ","));
				return jedis.zinterstore(dstkey, sets);
			}
		});
	}

	@Override
	public Long zinterstore(final String dstkey, final ZParams params, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zinterstore(dstkey, params, sets);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
	}

	@Override
	public Double zscore(final String key, final String member) {
		return (Double) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {

				return jedis.zscore(key, member);

			}
		});
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> sort(final String key) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sort(key);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> sort(final String key, final SortingParams sortingParameters) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sort(key, sortingParameters);
			}
		});
	}

	@Override
	public Long zcount(final String key, final double min, final double max) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	@Override
	public Long zcount(final String key, final String min, final String max) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count) {
		return (Set<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Long zremrangeByRank(final String key, final long start, final long end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
	}

	@Override
	public Long zremrangeByScore(final String key, final double start, final double end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	@Override
	public Long zremrangeByScore(final String key, final String start, final String end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	@Override
	public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.linsert(key, where, pivot, value);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> keys(final String pattern) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.keys(pattern);
			}
		});
	}

	@Override
	public Long zunionstore(final String dstkey, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zunionstore(dstkey, sets);
			}
		});
	}

	@Override
	public Long zunionstore(final String dstkey, final ZParams params, final String... sets) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zunionstore(dstkey, params, sets);
			}
		});
	}

	@Override
	public Long lpushx(final String key, final String string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpushx(key, string);
			}
		});
	}

	@Override
	public Long rpushx(final String key, final String string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpushx(key, string);
			}
		});
	}

	@Override
	public String getServerInfo() {
		return this.server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zunionStoreInJava(final String... sets) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Set<String> strings = new HashSet<String>(75 * sets.length);
				for (int i = 1; i < sets.length; i++) {
					Set<String> _sets = jedis.zrange(sets[i], 0, -1);
					strings.addAll(_sets);
				}
				return strings;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> zunionStoreByScoreInJava(final double min, final double max, final String... sets) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				Set<String> strings = new HashSet<String>(75 * sets.length);
				for (int i = 0; i < sets.length; i++) {
					Set<String> _sets = jedis.zrangeByScore(sets[i], min, max);
					strings.addAll(_sets);
				}
				return strings;
			}
		});
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		Long temp = (long) offset;
		return this.setrange(key, temp, value);
	}

	@Override
	public Object evalsha(final String sha1, final int keyCount, final String... params) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(sha1, keyCount, params);
			}
		});
	}

	@Override
	public Object evalsha(final String sha1, final List<String> keys, final List<String> args) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(sha1, keys, args);
			}
		});
	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		String sha1 = Integer.toString(script.hashCode());
		if (!sha1.equals(sha)) {
			throw new IllegalArgumentException("sha[" + sha + "][" + sha1 + "]值不对.");
		}
		return this.eval(script);
	}

	@Override
	public String evalReturnSha(String script) {
		this.eval(script);
		return Integer.toString(script.hashCode());
	}

	@Override
	public Object eval(final String script) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.eval(script);
			}
		});
	}

	@Override
	public Object eval(final String script, final int keyCount, final String... params) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.eval(script, keyCount, params);
			}
		});
	}

	@Override
	public Object evalsha(final String script) {
		return this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.evalsha(script);
			}
		});
	}

	@Override
	public String bgrewriteaof() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bgrewriteaof();
			}
		});
	}

	@Override
	public String bgsave() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bgsave();
			}
		});
	}

	@Override
	public String save() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.save();
			}
		});
	}

	@Override
	public IJedisPool getJedisPool() {
		return pool;
	}

	@Override
	public Long publish(final String channel, final String message) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.publish(channel, message);
			}
		});
	}

	@Override
	public void psubscribe(final JedisPubSub jedisPubSub, final String... patterns) {
		this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.psubscribe(jedisPubSub, patterns);
				return null;
			}
		});
	}

	@Override
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				jedis.subscribe(jedisPubSub, channels);
				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> sdiff(final String... keys) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sdiff(keys);
			}
		});
	}

	@Override
	public Long sdiffstore(final String dstkey, final String... keys) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sdiffstore(dstkey, keys);
			}
		});
	}

	@Override
	public String randomKey() {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.randomKey();
			}
		});
	}

	@Override
	public Long persist(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.persist(key);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final String value) {
		return (Boolean) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Long strlen(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.strlen(key);
			}
		});
	}

	@Override
	public Long lpushx(final String key, final String... string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.lpushx(key, string);
			}
		});
	}

	@Override
	public Long rpushx(final String key, final String... string) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.rpushx(key, string);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> blpop(final String arg) {
		return (List<String>) this.execute(new Invoker() {
			@SuppressWarnings("deprecation")
			@Override
			public Object execute(Jedis jedis) {
				return jedis.blpop(arg);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> brpop(final String arg) {
		return (List<String>) this.execute(new Invoker() {
			@SuppressWarnings("deprecation")
			@Override
			public Object execute(Jedis jedis) {
				return jedis.brpop(arg);
			}
		});
	}

	@Override
	public String echo(final String string) {
		return (String) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.echo(string);
			}
		});
	}

	@Override
	public Long move(final String key, final int dbIndex) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.move(key, dbIndex);
			}
		});
	}

	@Override
	public Long bitcount(final String key) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bitcount(key);
			}
		});
	}

	@Override
	public Long bitcount(final String key, final long start, final long end) {
		return (Long) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.bitcount(key, start, end);
			}
		});
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor) {
		return (ScanResult<Entry<String, String>>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		return (ScanResult<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return (ScanResult<Tuple>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
	}

	@Override
	public Long pfadd(String key, String... elements) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public long pfcount(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public String set(String key, String arg1, String arg2, String arg3, long arg4) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@PreDestroy
	@Override
	public void destroy() {
		if (pool != null) {
			pool.destroy();
		}
	}

	@Override
	public Long pexpire(String key, long milliseconds) {

		return null;
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {

		return null;
	}

	@Override
	public Double incrByFloat(String key, double value) {

		return null;
	}

	@Override
	public Set<String> spop(String key, long count) {

		return null;
	}

	@Override
	public Long zlexcount(String key, String min, String max) {

		return null;
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {

		return null;
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {

		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {

		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {

		return null;
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> blpop(final int timeout, final String key) {
		return (List<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.blpop(timeout, key);
			}
		});
	}

	@Override
	public List<String> brpop(int timeout, String key) {

		return null;
	}

	@Override
	public Long sadd(String key, long member) {
		return this.sadd(key, Long.toString(member));
	}

	@Override
	public Long srem(String key, long member) {
		return this.srem(key, Long.toString(member));
	}

	@Override
	public Long bitpos(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitpos(String arg0, boolean arg1, BitPosParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String arg0, Map<String, GeoCoordinate> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String arg0, double arg1, double arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double geodist(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double geodist(String arg0, String arg1, String arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> geohash(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoCoordinate> geopos(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4, GeoRadiusParam arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3, GeoRadiusParam arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double hincrByFloat(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String psetex(String arg0, long arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pttl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<String> sscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String arg0, Map<String, Double> arg1, ZAddParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String arg0, double arg1, String arg2, ZAddParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String arg0, double arg1, String arg2, ZIncrByParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Tuple> zscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> sinter(final String... keys) {
		return (Set<String>) this.execute(new Invoker() {
			@Override
			public Object execute(Jedis jedis) {
				return jedis.sinter(keys);
			}
		});
	}
}
