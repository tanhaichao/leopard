package io.leopard.redis.memory;

/**
 * Redis Key命令.
 * 
 * @author 阿海
 * 
 */
public interface IRedisKey {
	
	Boolean exists(String key);

	Long expire(String key, int seconds);

	Long del(String key);

	boolean flushAll();
}
