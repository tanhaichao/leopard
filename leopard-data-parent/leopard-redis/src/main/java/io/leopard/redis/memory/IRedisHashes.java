package io.leopard.redis.memory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisHashes extends IRedisKey {
	Long hset(String key, String field, String value);

	String hget(String key, String field);

	Long hsetnx(String key, String field, String value);

	String hmset(String key, Map<String, String> hash);

	List<String> hmget(String key, String... fields);

	Long hincrBy(String key, String field, long value);

	Boolean hexists(String key, String field);

	Long hdel(String key, String... field);

	Long hlen(String key);

	Set<String> hkeys(String key);

	List<String> hvals(String key);

	Map<String, String> hgetAll(String key);
}
