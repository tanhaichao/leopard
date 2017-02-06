package io.leopard.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * Jedis接口(只为显示参数)
 * 
 * @author 阿海
 * 
 */
public interface JedisCommands extends redis.clients.jedis.JedisCommands {

	@Override
	/**
	 * 如果 key 已经存在并且是一个字符串，将 value 追加到 key 原来的值的末尾， 如果 key 不存在，将给定 key 设为 value.
	 */
	public Long append(final String key, final String value);

	@Override
	/**
	 * 将字符串值 value 关联到 key，如果 key 已经持有其他值，覆写旧值.
	 */
	public String set(String key, String value);

	@Override
	/**
	 * 为给定 key 设置生存时间，当 key 过期时它会被自动删除.
	 */
	public Long expire(String key, int seconds);

	@Override
	/**
	 * 返回给定 key 的剩余生存时间，以秒为单位.
	 */
	public Long ttl(String key);

	@Override
	/**
	 * 将 key 中储存的数字值增一， 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作.
	 */
	public Long incr(String key);

	@Override
	/**
	 * 返回 key 所关联的字符串值.
	 */
	public String get(String key);

	@Override
	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值.
	 */
	public String getSet(String key, String value);

	@Override
	/**
	 * 返回有序集 key 的基数.
	 */
	public Long zcard(String key);

	@Override
	/**
	 * 返回有序集 key 中，指定区间内的成员和对应的score，有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end);

	@Override
	/**
	 * 返回有序集 key 中，指定区间内的成员，有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<String> zrevrange(final String key, final long start, final long end);

	@Override
	/**
	 * 返回有序集 key 中，指定区间内的成员，其中成员的位置按 score 值递增(从小到大)来排序.
	 */
	public Set<String> zrange(final String key, final long start, final long end);

	@Override
	/**
	 * 将元素及其 score 值加入到有序集 key 中.
	 */
	public Long zadd(final String key, final double score, final String member);

	@Override
	/**
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 中.
	 */
	public Long zadd(String key, Map<String, Double> scoreMembers);

	@Override
	/**
	 * 从偏移量 offset开始， 用 value覆写给定 key所储存的字符串值.
	 */
	public Long setrange(final String key, final long offset, final String value);

	@Override
	/**
	 * 移除集合 key 中的一个或多个 member 元素.
	 */
	public Long srem(final String key, final String... member);

	@Override
	/**
	 * 检查给定 key 是否存在.
	 */
	public Boolean exists(String key);

	@Override
	/**
	 * 返回 key 所储存的值的类型.
	 */
	public String type(String key);

	@Override
	/**
	 * 为 key 设置生存时间，时间参数是 UNIX 时间戳.
	 */
	public Long expireAt(String key, long unixTime);

	@Override
	/**
	 * 对 key 所储存的字符串值，设置或清除指定偏移量上的位.
	 */
	public Boolean setbit(String key, long offset, boolean value);

	@Override
	/**
	 * 对 key 所储存的字符串值，获取指定偏移量上的位.
	 */
	public Boolean getbit(String key, long offset);

	@Override
	/**
	 * 返回 key 中字符串值中指定范围的子字符串.
	 */
	public String getrange(String key, long startOffset, long endOffset);

	@Override
	/**
	 * 将 key 的值设为 value.
	 */
	public Long setnx(String key, String value);

	@Override
	/**
	 * 将值 value 关联到 key ，并设置 key 的生存时间，以秒为单位.
	 */
	public String setex(String key, int seconds, String value);

	@Override
	/**
	 * 将 key 所储存的值减去减量.
	 */
	public Long decrBy(String key, long integer);

	@Override
	/**
	 * 将 key中储存的数字值减一.
	 */
	public Long decr(String key);

	@Override
	/**
	 * 将 key 所储存的值加上增量 .
	 */
	public Long incrBy(String key, long integer);

	@Override
	/**
	 * 返回 key 中字符串值中指定范围的子字符串，redis 2.0以前的版本使用.
	 */
	public String substr(String key, int start, int end);

	@Override
	/**
	 * 将哈希表 key 中的域 field 的值设为 value.
	 */
	public Long hset(String key, String field, String value);

	@Override
	/**
	 * 返回哈希表 key 中给定域 field 的值.
	 */
	public String hget(String key, String field);

	@Override
	/**
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在.
	 */
	public Long hsetnx(String key, String field, String value);

	@Override
	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中.
	 */
	public String hmset(String key, Map<String, String> hash);

	@Override
	/**
	 * 返回哈希表 key 中，一个或多个给定域的值.
	 */
	public List<String> hmget(String key, String... fields);

	@Override
	/**
	 * 为哈希表 key 中的域 field 的值加上增量 .
	 */
	public Long hincrBy(String key, String field, long value);

	@Override
	/**
	 * 判断哈希表 key 中，给定域 field 是否存在.
	 */
	public Boolean hexists(String key, String field);

	@Override
	/**
	 * 删除哈希表 key 中的一个或多个指定域.
	 */
	public Long hdel(String key, String... field);

	@Override
	/**
	 * 返回哈希表 key 中域的数量.
	 */
	public Long hlen(String key);

	@Override
	/**
	 * 返回哈希表 key 中的所有域.
	 */
	public Set<String> hkeys(String key);

	@Override
	/**
	 * 返回哈希表 key 中所有域的值.
	 */
	public List<String> hvals(String key);

	@Override
	/**
	 * 返回哈希表 key 中，所有的域和值.
	 */
	public Map<String, String> hgetAll(String key);

	@Override
	/**
	 * 将一个或多个值插入到列表 key 的表尾(最右边).
	 */
	public Long rpush(String key, String... strings);

	@Override
	/**
	 * 将一个或多个值插入到列表 key 的表头.
	 */
	public Long lpush(String key, String... strings);

	@Override
	/**
	 * 返回列表 key 的长度.
	 */
	public Long llen(String key);

	@Override
	/**
	 * 返回列表 key 中指定区间内的元素.
	 */
	public List<String> lrange(String key, long start, long end);

	@Override
	/**
	 * 保留列表指定区间内的元素，删除其余的元素.
	 */
	public String ltrim(String key, long start, long end);

	@Override
	/**
	 * 返回列表 key 中，下标为 index 的元素， 0表示第一个元素，1表示第二个元素，-1表示最后一个元素，-2表示倒数第二个元素，以此类推.
	 */
	public String lindex(String key, long index);

	@Override
	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value.
	 */
	public String lset(String key, long index, String value);

	@Override
	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素， count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值 count = 0 : 移除表中所有与 value 相等的值.
	 */
	public Long lrem(String key, long count, String value);

	@Override
	/**
	 * 移除并返回列表 key 的头元素.
	 */
	public String lpop(String key);

	@Override
	/**
	 * 移除并返回列表 key 的尾元素.
	 */
	public String rpop(String key);

	@Override
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略. 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 */
	public Long sadd(String key, String... members);

	@Override
	/**
	 * 返回集合 key 中的所有成员.
	 */
	public Set<String> smembers(String key);

	@Override
	/**
	 * 移除并返回集合中的一个随机元素.
	 */
	public String spop(String key);

	@Override
	/**
	 * 返回集合 key 的基数.
	 */
	public Long scard(String key);

	@Override
	/**
	 * 判断 member 元素是否集合 key 的成员.
	 */
	public Boolean sismember(String key, String member);

	@Override
	/**
	 * 返回集合key 中的一个随机元素.
	 */
	public String srandmember(String key);

	@Override
	/**
	 * 移除有序集 key 中的一个或多个成员.
	 */
	public Long zrem(String key, String... members);

	@Override
	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量.
	 */
	public Double zincrby(String key, double score, String member);

	@Override
	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)排列.
	 */
	public Long zrank(String key, String member);

	@Override
	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序.
	 */
	public Long zrevrank(String key, String member);

	@Override
	/**
	 * 返回有序集 key 中指定区间内的成员。其中成员的位置按 score 值递增(从小到大)来排序.
	 */
	public Set<Tuple> zrangeWithScores(String key, long start, long end);

	@Override
	/**
	 * 返回有序集 key 中，成员 member 的 score 值
	 */
	public Double zscore(String key, String member);

	@Override
	/**
	 * 返回给定列表、集合、有序集合 key 中从小到大排序的结果.
	 */
	public List<String> sort(String key);

	@Override
	/**
	 * 返回给定列表、集合、有序集合 key 中按指定排序方法排序的结果.
	 */
	public List<String> sort(String key, SortingParams sortingParameters);

	@Override
	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量.
	 */
	public Long zcount(String key, double min, double max);

	@Override
	/**
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量.
	 */
	public Long zcount(String key, String min, String max);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<String> zrangeByScore(String key, double min, double max);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<String> zrangeByScore(String key, String min, String max);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<String> zrevrangeByScore(String key, double max, double min);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<String> zrevrangeByScore(String key, String max, String min);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

	@Override
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递增(从小到大)次序排列.
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	@Override
	/**
	 * 返回有序集 key指定范围区间内 score 值介于 min 和 max 之间的成员和对应的score值， 有序集成员按 score 值递减(从大到小)次序排列.
	 */
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	@Override
	/**
	 * 移除有序集 key 中，指定排名区间内的所有成员.
	 */
	public Long zremrangeByRank(String key, long start, long end);

	@Override
	/**
	 * 移除有序集 key 中，所有 score 值介于 start 和 end 之间的成员.
	 */
	public Long zremrangeByScore(String key, double start, double end);

	@Override
	/**
	 * 移除有序集 key 中，所有 score 值介于 start 和 end 之间的成员.
	 */
	public Long zremrangeByScore(String key, String start, String end);

	@Override
	/**
	 * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后.
	 */
	public Long linsert(String key, LIST_POSITION where, String pivot, String value);

	/**
	 * 将值 value 插入到列表 key 的表头.
	 */
	public Long lpushx(String key, String string);

	/**
	 * 将值 value 插入到列表 key 的表尾.
	 */
	public Long rpushx(String key, String string);

	// jedis 2.5.1

	public Long pfadd(String key, String... elements);

	public long pfcount(String key);

	public String set(String key, String arg1, String arg2, String arg3, long arg4);

	List<String> srandmember(String key, int count);

}
