package io.leopard.redis.util;

import io.leopard.redis.JedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolApacheImpl implements IJedisPool {
	private JedisPool pool;

	public JedisPoolApacheImpl(String host, int port, int timeout, int maxActive, String password) {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// poolConfig.setMaxActive(maxActive);
		poolConfig.setMaxTotal(maxActive);
		poolConfig.setMaxIdle(maxActive);
		// poolConfig.setMinEvictableIdleTimeMillis(24 * 3600 * 1000);
		poolConfig.setMinEvictableIdleTimeMillis(-1);// ahai 20131024 已建立的连接不回收，高并发时建立连接会很耗资源
		// poolConfig.setTimeBetweenEvictionRunsMillis(-1);

		pool = new JedisPool(poolConfig, host, port, timeout, password);
		// System.err.println("pool:"+pool);
	}

	@Override
	public Jedis getResource() {
		return pool.getResource();
	}

	@Override
	public void returnBrokenResource(Jedis jedis) {
		pool.returnBrokenResource(jedis);
	}

	@Override
	public void returnResource(Jedis jedis) {
		pool.returnResource(jedis);
	}

	@Override
	public void destroy() {
		pool.destroy();
	}
}
