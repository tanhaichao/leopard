package io.leopard.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class JedisPool extends redis.clients.jedis.JedisPool {

	public JedisPool() {
		super();
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
		super(poolConfig, host);
	}

	public JedisPool(String host, int port) {
		super(host, port);
	}

	public JedisPool(final String host) {
		super(host);
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout, final String password) {
		super(poolConfig, host, port, timeout, password);
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port) {
		super(poolConfig, host, port);
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port, final int timeout) {
		super(poolConfig, host, port, timeout);
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout, final String password, final int database) {
		super(poolConfig, host, port, timeout, password, database);
	}

	public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port, int timeout, final String password, final int database, final String clientName) {
		super(poolConfig, host, port, timeout, password, database, clientName);
	}

	public redis.clients.jedis.Jedis getResource() {
		redis.clients.jedis.Jedis jedis = super.getResource();
		// System.out.println("\ngetResource:" + jedis);
		// if (true) {
		// return jedis;
		// }
		if (jedis instanceof JedisWrapper) {
			// System.out.println("JedisWrapper:" + jedis);
			return jedis;
		}
		return DriverManager.wrapper(jedis);
		// System.out.println("getResource:" + jedis);
		// return jedis;
	}

	@Deprecated
	public void returnBrokenResource(final redis.clients.jedis.Jedis resource) {
		super.returnBrokenResource(resource);
	}

	@Deprecated
	public void returnResource(redis.clients.jedis.Jedis resource) {
		while (resource instanceof JedisWrapper) {
			resource = ((JedisWrapper) resource).getJedis();
			// System.out.println("returnResource:" + resource);
		}
		super.returnResource(resource);
	}
}
