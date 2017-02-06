package io.leopard.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;

/**
 * Redis连接监听器.
 * 
 * @author ahai
 * 
 */
public interface RedisConnectionListener {

	/**
	 * 设置连接池配置信息.
	 * 
	 * @param host
	 * @param port
	 * @param timeout
	 * @return
	 */
	void setPoolConfig(String host, int port, int timeout, int maxPoolSize, GenericObjectPool<Jedis> pool);

	/**
	 * 打开连接
	 * 
	 * @return
	 */
	void open(Jedis resource, long startTime);

	/**
	 * 关闭连接.
	 * 
	 * @return
	 */
	void close(Jedis resource);

	/**
	 * 中断连接.
	 * 
	 * @return
	 */
	void broken();
}
