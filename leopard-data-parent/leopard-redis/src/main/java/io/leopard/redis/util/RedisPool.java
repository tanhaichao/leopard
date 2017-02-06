package io.leopard.redis.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池配置
 * 
 * @author 阿海
 * 
 */
public class RedisPool extends JedisPool {
	private Log logger = LogFactory.getLog(this.getClass());

	private String server;
	private int timeout;

	public RedisPool(String server, int timeout) {
		super(getConfig(), parseHost(server), parsePort(server), timeout);
		this.server = server;
		this.timeout = timeout;
	}

	public void init() {
		logger.info("init");
	}

	public void destroy() {
		logger.info("destroy");
		super.destroy();
	}

	public String getServer() {
		return server;
	}

	public int getTimeout() {
		return timeout;
	}

	public static GenericObjectPoolConfig getConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// poolConfig.setMaxActive(8);// CPU数量即可

		poolConfig.setMaxTotal(8);// CPU数量即可
		return poolConfig;
	}

	protected static String parseHost(String server) {
		String[] serverInfo = server.split(":");
		return serverInfo[0].trim();
	}

	protected static int parsePort(String server) {
		String[] serverInfo = server.split(":");
		int port = Integer.parseInt(serverInfo[1].trim());
		return port;
	}

}
