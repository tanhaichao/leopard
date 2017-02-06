package io.leopard.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.leopard.redis.memory.IRedisHashes;
import io.leopard.redis.memory.IRedisList;
import io.leopard.redis.memory.IRedisSet;
import io.leopard.redis.memory.IRedisSortedSet;
import io.leopard.redis.memory.IRedisString;
import io.leopard.redis.memory.RedisHashesImpl;
import io.leopard.redis.memory.RedisListImpl;
import io.leopard.redis.memory.RedisSetImpl;
import io.leopard.redis.memory.RedisSortedSetImpl;
import io.leopard.redis.memory.RedisStringImpl;
import io.leopard.redis.util.IJedisPool;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**
 * Redis接口内存实现.
 * 
 * @author 阿海
 * 
 */
public class RedisMemoryImpl implements Redis {

	private IRedisString redisString = new RedisStringImpl();
	private IRedisHashes redisHashes = new RedisHashesImpl();
	private IRedisSortedSet redisSortedSet = new RedisSortedSetImpl();
	private IRedisSet redisSet = new RedisSetImpl();
	private IRedisList redisList = new RedisListImpl();

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public Long append(String key, String value) {
		String current = this.get(key);
		current = current == null ? "" : current;
		String str = current + value;
		this.set(key, str);
		return (long) str.length();
		// throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public String set(String key, String value) {
		return this.redisString.set(key, value);
	}

	@Override
	public Long del(String... keys) {
		long count = 0;
		for (String key : keys) {
			long result = this.decr(key);
			count += result;
		}
		return count;
	}

	@Override
	public Long del(String key) {
		Long result = redisString.del(key);
		if (result == 0) {
			//
		}
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Long result = redisString.expire(key, seconds);
		if (result == 0) {
			//
		}
		return result;
	}

	@Override
	public Long ttl(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long incr(String key) {
		return this.redisString.incr(key);
	}

	@Override
	public String get(String key) {
		return this.redisString.get(key);
	}

	@Override
	public String getSet(String key, String value) {
		String current = this.get(key);
		this.set(key, value);
		return current;
	}

	@Override
	public Long zcard(String key) {
		return this.redisSortedSet.zcard(key);
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return this.redisSortedSet.zrevrangeWithScores(key, start, end);
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		return this.redisSortedSet.zrevrange(key, start, end);
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		return this.redisSortedSet.zrange(key, start, end);
	}

	@Override
	public Long zadd(String key, double score, String member) {
		return this.redisSortedSet.zadd(key, score, member);
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long srem(String key, String... member) {
		return this.redisSet.srem(key, member);
	}

	@Override
	public Boolean exists(String key) {
		if (this.redisString.exists(key)) {
			return true;
		}
		else if (this.redisList.exists(key)) {
			return true;
		}
		else if (this.redisSet.exists(key)) {
			return true;
		}
		else if (this.redisSortedSet.exists(key)) {
			return true;
		}
		else if (this.redisHashes.exists(key)) {
			return true;
		}
		return false;
	}

	@Override
	public String type(String key) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Long expireAt(String key, long unixTime) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Boolean getbit(String key, long offset) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		String value = this.get(key);
		if (value == null || value.isEmpty()) {
			return null;
		}

		if (startOffset < 0) {
			startOffset = value.length() + startOffset;
		}
		// throw new UnsupportedOperationException("Not Implemented");
		if (endOffset == -1) {
			return value.substring((int) startOffset);
		}

		int endIndex = (int) endOffset + 1;
		if (endIndex > value.length()) {
			endIndex = value.length();
		}

		return value.substring((int) startOffset, endIndex);
	}

	@Override
	public Long setnx(String key, String value) {
		return redisString.setnx(key, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		return this.redisString.setex(key, seconds, value);
	}

	@Override
	public Long decrBy(String key, long integer) {
		return this.redisString.decrBy(key, integer);
	}

	@Override
	public Long decr(String key) {
		return this.redisString.decr(key);
	}

	@Override
	public Long incrBy(String key, long integer) {
		return this.redisString.incrBy(key, integer);
	}

	@Override
	public String substr(String key, int start, int end) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long hset(String key, String field, String value) {
		return redisHashes.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return redisHashes.hget(key, field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		return redisHashes.hsetnx(key, field, value);
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		return redisHashes.hmset(key, hash);
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		return redisHashes.hmget(key, fields);
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		return redisHashes.hincrBy(key, field, value);
	}

	@Override
	public Boolean hexists(String key, String field) {
		return redisHashes.hexists(key, field);
	}

	@Override
	public Long hdel(String key, String... fields) {
		return redisHashes.hdel(key, fields);
	}

	@Override
	public Long hlen(String key) {
		return redisHashes.hlen(key);
	}

	@Override
	public Set<String> hkeys(String key) {
		return redisHashes.hkeys(key);
	}

	@Override
	public List<String> hvals(String key) {
		return redisHashes.hvals(key);
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return redisHashes.hgetAll(key);
	}

	@Override
	public Long rpush(String key, String... strings) {
		return this.redisList.rpush(key, strings);
	}

	@Override
	public Long lpush(String key, String... strings) {
		return this.redisList.lpush(key, strings);
	}

	@Override
	public Long llen(String key) {
		return this.redisList.llen(key);
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		return this.redisList.lrange(key, start, end);
	}

	@Override
	public String ltrim(String key, long start, long end) {
		return this.redisList.ltrim(key, start, end);
	}

	@Override
	public String lindex(String key, long index) {
		return this.redisList.lindex(key, index);
	}

	@Override
	public String lset(String key, long index, String value) {
		return this.redisList.lset(key, index, value);
	}

	@Override
	public Long lrem(String key, long count, String value) {
		return this.redisList.lrem(key, count, value);
	}

	@Override
	public String lpop(String key) {
		return this.redisList.lpop(key);
	}

	@Override
	public String rpop(String key) {
		return this.redisList.rpop(key);
	}

	@Override
	public Set<String> smembers(String key) {
		return this.redisSet.smembers(key);
	}

	@Override
	public String spop(String key) {
		return this.redisSet.spop(key);
	}

	@Override
	public Long scard(String key) {
		return this.redisSet.scard(key);
	}

	@Override
	public Boolean sismember(String key, String member) {
		return this.redisSet.sismember(key, member);
	}

	@Override
	public String srandmember(String key) {
		return this.redisSet.srandmember(key);
	}

	@Override
	public Long zrem(String key, String... members) {
		return redisSortedSet.zrem(key, members);
	}

	@Override
	public Double zincrby(String key, double score, long member) {
		return this.zincrby(key, score, Long.toString(member));
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		return redisSortedSet.zincrby(key, score, member);
	}

	@Override
	public Long zrank(String key, String member) {
		return redisSortedSet.zrank(key, member);
	}

	@Override
	public Long zrevrank(String key, String member) {
		return redisSortedSet.zrevrank(key, member);
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return redisSortedSet.zrangeWithScores(key, start, end);
	}

	@Override
	public Double zscore(String key, String member) {
		return redisSortedSet.zscore(key, member);
	}

	@Override
	public Double zscore(final String key, final long member) {
		return this.zscore(key, Long.toString(member));
	}

	@Override
	public List<String> sort(String key) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Long zcount(String key, double min, double max) {
		return redisSortedSet.zcount(key, min, max);
	}

	@Override
	public Long zcount(String key, String min, String max) {
		return redisSortedSet.zcount(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		return redisSortedSet.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		return redisSortedSet.zrangeByScore(key, min, max);
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		return redisSortedSet.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return redisSortedSet.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		return redisSortedSet.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return redisSortedSet.zrevrangeByScore(key, max, min);
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		return redisSortedSet.zrangeByScore(key, min, max, offset, count);
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		return redisSortedSet.zrevrangeByScore(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min);
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		return redisSortedSet.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		return redisSortedSet.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		return redisSortedSet.zremrangeByRank(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		return redisSortedSet.zremrangeByScore(key, start, end);
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		return redisSortedSet.zremrangeByScore(key, start, end);
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Long lpushx(String key, String string) {
		return this.redisList.lpushx(key, string);
	}

	@Override
	public Long rpushx(String key, String string) {
		return this.redisList.rpushx(key, string);
	}

	@Override
	public IJedisPool getJedisPool() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Jedis getResource() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public boolean append(String key, String value, int seconds) {
		this.append(key, value);
		this.expire(key, seconds);
		return true;
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return this.redisString.setrange(key, offset, value);
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		return this.setrange(key, (long) offset, value);
	}

	@Override
	public Long sadd(String key, String... members) {
		return this.redisSet.sadd(key, members);
	}

	@Override
	public Transaction multi() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public boolean flushAll() {
		return this.redisString.flushAll();
		// return true;
	}

	@Override
	public boolean flushDB() {
		return this.redisString.flushAll();
		// return true;
	}

	@Override
	public RedisInfo info() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public long getUsedMemory() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public long dbSize() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String set(String key, String value, int seconds) {
		return this.setex(key, seconds, value);
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		for (int i = 0; i < keyList.size(); i++) {
			String key = keyList.get(i);
			String value = valueList.get(i);
			this.set(key, value);
		}
		return true;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public void returnResource(Jedis jedis) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public List<String> mget(String... keys) {
		List<String> list = new ArrayList<String>();
		for (String key : keys) {
			list.add(this.get(key));
		}
		return list;
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		return this.redisSortedSet.zinterstore(dstkey, sets);
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		return this.redisSortedSet.zinterstore(dstkey, params, sets);
	}

	@Override
	public Set<String> keys(String pattern) {
		return null;
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		return this.redisSortedSet.zunionstore(dstkey, sets);
	}

	@Override
	public String getServerInfo() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	// @Override
	// public String hget(String key, int field) {
	// return this.hget(key, Integer.toString(field));
	// }

	@Override
	public String hget(String key, long field) {
		return this.hget(key, Long.toString(field));
	}

	// @Override
	// public Long hset(String key, int field, String value) {
	// return this.hset(key, Integer.toString(field), value);
	// }

	@Override
	public Long hset(String key, long field, String value) {
		return this.hset(key, Long.toString(field), value);
	}

	// @Override
	// public Long hdel(String key, int field) {
	// return this.hdel(key, Integer.toString(field));
	// }

	@Override
	public Long hdel(String key, long field) {
		return this.hdel(key, Long.toString(field));
	}

	@Override
	public Long zadd(String key, double score, long member) {
		return redisSortedSet.zadd(key, score, Long.toString(member));
	}

	@Override
	public Long zrem(String key, long member) {
		return redisSortedSet.zrem(key, member);
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		return redisSortedSet.zunionStoreInJava(sets);
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		return redisSortedSet.zunionStoreByScoreInJava(min, max, sets);
	}

	// @Override
	// public Long zadd2(String key, Map<String, Double> scoreMembers) {
	// return redisSortedSet.zadd2(key, scoreMembers);
	// }

	@Override
	public Object evalsha(String script) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Object eval(String script) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String evalReturnSha(String script) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String bgrewriteaof() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String bgsave() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public String save() {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Long publish(String channel, String message) {
		JedisPubSub jedisPubSub = this.channelMap.get(channel);
		if (jedisPubSub != null) {
			jedisPubSub.onMessage(channel, message);
		}
		return 1L;
	}

	private Map<String, JedisPubSub> channelMap = new ConcurrentHashMap<String, JedisPubSub>();

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		for (String channel : channels) {
			channelMap.put(channel, jedisPubSub);
		}
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		throw new UnsupportedOperationException("Not Implemented");

	}

	@Override
	public Set<String> sdiff(String... keys) {
		return this.redisSet.sdiff(keys);
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		return this.redisSortedSet.zunionstore(dstkey, params, sets);
	}

	@Override
	public String randomKey() {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long persist(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long strlen(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long lpushx(String key, String... string) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long rpushx(String key, String... string) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public List<String> blpop(String arg) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public List<String> brpop(String arg) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public String echo(String string) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long move(String key, int dbIndex) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long bitcount(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long pfadd(String key, String... elements) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public long pfcount(String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public String set(String key, String arg1, String arg2, String arg3, long arg4) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public List<String> srandmember(String key, int count) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Double incrByFloat(String key, double value) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Set<String> spop(String key, long count) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long zlexcount(String key, String min, String max) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long sadd(String key, long member) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long srem(String key, long member) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public Long bitpos(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitpos(String arg0, boolean arg1, BitPosParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String arg0, Map<String, GeoCoordinate> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long geoadd(String arg0, double arg1, double arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double geodist(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double geodist(String arg0, String arg1, String arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> geohash(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoCoordinate> geopos(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadius(String arg0, double arg1, double arg2, double arg3, GeoUnit arg4, GeoRadiusParam arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(String arg0, String arg1, double arg2, GeoUnit arg3, GeoRadiusParam arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double hincrByFloat(String arg0, String arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String psetex(String arg0, long arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pttl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<String> sscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String arg0, Map<String, Double> arg1, ZAddParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String arg0, double arg1, String arg2, ZAddParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String arg0, double arg1, String arg2, ZIncrByParams arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Tuple> zscan(String arg0, String arg1, ScanParams arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> sinter(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}
}
