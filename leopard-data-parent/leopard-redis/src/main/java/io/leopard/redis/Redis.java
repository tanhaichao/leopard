package io.leopard.redis;

import io.leopard.redis.util.IJedisPool;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

/**
 * Redis接口.
 * 
 * Redis操作.
 * 
 * @category public
 * 
 * @author 阿海
 * 
 */
public interface Redis extends JedisCommands {

	/**
	 * 容器初始化
	 */
	void init();

	/**
	 * 容器销毁
	 */
	void destroy();

	/**
	 * 返回redis连接池.
	 * 
	 * @return
	 */
	IJedisPool getJedisPool();

	/**
	 * 返回jedis对象.
	 * 
	 * @return
	 */
	Jedis getResource();

	/**
	 * 将value追加到key的末尾，若key不存在，则把key的值设为value，事务安全的.
	 * 
	 * @param key key
	 * @param value value
	 * @param seconds 客户端超时时长
	 * @return
	 */
	boolean append(String key, String value, int seconds);

	/**
	 * 将 key 改名为 newkey
	 * 
	 * @param oldkey 旧key
	 * @param newkey 新key
	 * @return
	 */
	boolean rename(String oldkey, String newkey);

	/**
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始.
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	Long setrange(String key, long offset, String value);

	/**
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始.
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return
	 */
	Long setrange(String key, int offset, String value);

	/**
	 * 标记一个事务块的开始.
	 * 
	 * @return
	 */
	Transaction multi();

	/**
	 * 清空当前数据库中的所有 key.
	 * 
	 * @return
	 */
	boolean flushDB();

	/**
	 * 返回关于 Redis 服务器的各种信息和统计数值.
	 * 
	 * @return
	 */
	RedisInfo info();

	/**
	 * 将 key 改名为 newkey.
	 * 
	 * @param oldkey
	 * @param newkey
	 * @param seconds
	 * @return
	 */
	boolean rename(String oldkey, String newkey, int seconds);

	/**
	 * 返回由 Redis 分配器分配的内存总量，以字节（byte）为单位.
	 * 
	 * @return
	 */
	long getUsedMemory();

	/**
	 * 返回当前数据库的 key 的数量.
	 * 
	 * @return
	 */
	long dbSize();

	/**
	 * 将字符串值 value 关联到 key，并设置生存时间.
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	String set(String key, String value, int seconds);

	/**
	 * 删除整个 Redis 服务器的所有 key.
	 * 
	 * @return
	 */
	boolean flushAll();

	/**
	 * 批量设置key和value，事务安全的.
	 * 
	 * @param keyList
	 * @param valueList
	 * @return
	 */
	boolean set(List<String> keyList, List<String> valueList);

	/**
	 * 批量将value追加到对应key的末尾，并设置生存时间，事务安全的.
	 * 
	 * @param keyList
	 * @param valueList
	 * @param seconds
	 * @return
	 */
	boolean append(List<String> keyList, List<String> valueList, int seconds);

	/**
	 * 删除一个或多个 key.
	 * 
	 * @param keys
	 * @return
	 */
	Long del(String... keys);

	/**
	 * 删除 key.
	 * 
	 * @param key
	 * @return
	 */
	Long del(String key);

	/**
	 * 释放jedis对象到池中.
	 * 
	 * @param jedis
	 */
	void returnResource(Jedis jedis);

	/**
	 * 返回一个或多个 key 的值.
	 * 
	 * @param keys
	 * @return
	 */
	List<String> mget(final String... keys);

	/**
	 * 计算给定的一个或多个有序集的交集，并将该交集(结果集)储存到 dstkey.
	 * 
	 * @param dstkey 结果集
	 * @param sets 有续集
	 * @return
	 */
	Long zinterstore(String dstkey, final String... sets);

	/**
	 * 计算给定的一个或多个有序集的交集，将该交集(结果集)储存到 dstkey，并指定交集的结果集的聚合方式.
	 * 
	 * @param dstkey 结果集
	 * @param params 聚合方式
	 * @param sets 有续集
	 * @return
	 */
	Long zinterstore(final String dstkey, final ZParams params, final String... sets);

	/**
	 * 查找所有符合给定模式 pattern 的 key.
	 * 
	 * @param pattern 模式
	 * @return
	 */
	Set<String> keys(String pattern);

	/**
	 * 计算给定的一个或多个有序集的并集，并将该并集(结果集)储存到 dstkey.
	 * 
	 * @param dstkey
	 * @param sets
	 * @return
	 */
	Long zunionstore(final String dstkey, final String... sets);

	Long zunionstore(String dstkey, ZParams params, String... sets);

	/**
	 * 返回服务器信息.
	 * 
	 * @return
	 */
	String getServerInfo();

	// /**
	// * 返回哈希表 key 中给定域 field 的值.
	// *
	// * @param key
	// * @param field
	// * @return
	// */
	// String hget(String key, int field);

	/**
	 * 返回哈希表 key 中给定域 field 的值.
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	String hget(String key, long field);

	/**
	 * 将哈希表 key 中的域 field 的值设为 value.
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	// Long hset(String key, int field, String value);

	Long hset(String key, long field, String value);

	// /**
	// * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略.
	// *
	// * @param key
	// * @param field
	// * @return
	// */
	// Long hdel(String key, int field);

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略.
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, long field);

	/**
	 * 将 member 元素及其 score 值加入到有序集 key 中.
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	Long zadd(String key, double score, long member);

	/**
	 * 返回有序集 key 中，成员 member 的 score 值
	 */
	Double zscore(String key, long member);

	/**
	 * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略.
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	Long zrem(String key, long member);

	/**
	 * 返回给定的一个或多个有序集的并集.
	 * 
	 * @param sets
	 * @return
	 */
	Set<String> zunionStoreInJava(final String... sets);

	/**
	 * 返回给定的一个或多个有序集的并集 中，并集中的成员的 score 值介于 min 和 max 之间(包括等于 min 或 max )，有序集成员按 score 值递增(从小到大)次序排列.
	 * 
	 * @param min
	 * @param max
	 * @param sets
	 * @return
	 */
	Set<String> zunionStoreByScoreInJava(double min, double max, final String... sets);

	// /**
	// * 将多个 member元素及其 score 值加入到有序集 key 中.
	// *
	// * @param key
	// * key
	// * @param scoreMembers
	// * 元素及其score
	// * @return
	// */
	// @Deprecated
	// Long zadd2(String key, Map<String, Double> scoreMembers);

	/**
	 * 根据给定的 sha1校验码，对缓存在服务器中的脚本进行求值.
	 * 
	 * @param script sha1 校验码
	 * @return
	 */
	Object evalsha(String script);

	/**
	 * 输入脚本进行求值.
	 * 
	 * @param script 脚本
	 * @return
	 */
	Object eval(String script);

	/**
	 * 输入脚本进行求值.
	 * 
	 * @param script 脚本
	 * @param keyCount 键名参数的个数
	 * @param params 非键名参数的附加参数
	 * @return
	 */
	Object eval(String script, int keyCount, String... params);

	/**
	 * 根据给定的sha1校验码，对缓存在服务器中的脚本进行求值.
	 * 
	 * @param sha1 sha1校验码
	 * @param keys 键名参数
	 * @param args 非键名参数的附加参数
	 * @return
	 */
	Object evalsha(String sha1, List<String> keys, List<String> args);

	/**
	 * 根据给定的sha1校验码，对缓存在服务器中的脚本进行求值.
	 * 
	 * @param sha1 sha1校验码
	 * @param keyCount 键名参数的个数
	 * @param params 非键名参数的附加参数
	 * @return
	 */
	Object evalsha(String sha1, int keyCount, String... params);

	// /**
	// * 返回Lua扩展接口.
	// *
	// * @return
	// */
	// Ludis getLudis();

	/**
	 * 对脚本进行求值，并返回对脚本进行sha1加密后的值.
	 * 
	 * @param script 脚本
	 * @return
	 */
	String evalReturnSha(String script);

	/**
	 * 判断脚本进行sha1加密后的值是否与给定的sha1校验码相同，相同则对脚本进行求值.
	 * 
	 * @param sha sha1校验码
	 * @param script 脚本
	 * @return
	 */
	Object evalAssertSha(String sha, String script);

	/**
	 * 在后台执行一个 AOF文件 重写操作.
	 * 
	 * @return
	 */
	String bgrewriteaof();

	/**
	 * 在后台异步保存当前数据库的数据到磁盘.
	 * 
	 * @return
	 */
	String bgsave();

	/**
	 * 执行同步保存操作，将当前Redis实例的所有数据快照以 RDB 文件的形式保存到硬盘.
	 * 
	 * @return
	 */
	String save();

	Long publish(String channel, String message);

	void psubscribe(JedisPubSub jedisPubSub, String... patterns);

	void subscribe(JedisPubSub jedisPubSub, String... channels);

	Set<String> sdiff(final String... keys);

	Long sadd(String key, long member);

	Long srem(String key, long member);

	Long sdiffstore(final String dstkey, final String... keys);

	Set<String> sinter(final String... keys);

	/**
	 * 随机获取一个key
	 * 
	 * @return
	 */
	String randomKey();

	// 2.7.2
	Long pexpire(String key, long milliseconds);

	Long pexpireAt(String key, long millisecondsTimestamp);

	Double incrByFloat(String key, double value);

	Set<String> spop(String key, long count);

	Long zlexcount(String key, String min, String max);

	Set<String> zrangeByLex(String key, String min, String max);

	Set<String> zrangeByLex(String key, String min, String max, int offset, int count);

	Set<String> zrevrangeByLex(String key, String max, String min);

	Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count);

	Long zremrangeByLex(String key, String min, String max);

	List<String> blpop(int timeout, String key);

	List<String> brpop(int timeout, String key);

	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量.
	 */
	Double zincrby(String key, double score, long member);
}
