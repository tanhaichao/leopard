package io.leopard.redis.memory;

import java.util.Set;

public interface IRedisSet extends IRedisKey {
	Long sadd(String key, String... members);

	Set<String> smembers(String key);

	Set<String> sdiff(String... keys);

	public String spop(String key);

	Long scard(String key);

	Boolean sismember(String key, String member);

	String srandmember(String key);

	Long srem(String key, String... member);
}
