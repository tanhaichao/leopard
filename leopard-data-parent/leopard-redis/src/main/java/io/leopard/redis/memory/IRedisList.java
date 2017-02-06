package io.leopard.redis.memory;

import java.util.List;

public interface IRedisList extends IRedisKey {

	Long rpush(String key, String... strings);

	Long lpush(String key, String... strings);

	Long llen(String key);

	List<String> lrange(String key, long start, long end);

	String ltrim(String key, long start, long end);

	String lindex(String key, long index);

	String lset(String key, long index, String value);

	Long lrem(String key, long count, String value);

	String lpop(String key);

	String rpop(String key);

	Long lpushx(String key, String string);

	Long rpushx(String key, String string);
}
