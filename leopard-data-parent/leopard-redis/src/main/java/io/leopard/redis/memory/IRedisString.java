package io.leopard.redis.memory;


public interface IRedisString extends IRedisKey {

	Long setnx(String key, String value);

	String set(String key, String value);

	String get(String key);

	Long incr(String key);

	Long decrBy(String key, long integer);

	Long decr(String key);

	Long incrBy(String key, long integer);

	String setex(String key, int seconds, String value);

	Long setrange(String key, long offset, String value);
}
