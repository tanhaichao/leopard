package io.leopard.redis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

public class RedisWrapper implements Redis {

	private Redis redis;

	public Redis getRedis() {
		return redis;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	@Override
	public Long append(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(String key, String... member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String type(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expireAt(String key, long unixTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setbit(String key, long offset, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getbit(String key, long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setnx(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decrBy(String key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decr(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incrBy(String key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String substr(String key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hexists(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(String key, String... field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hlen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> hkeys(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> hvals(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(String key, String... strings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String... strings) {
		return this.getRedis().lpush(key, strings);
	}

	@Override
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> lrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ltrim(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lindex(String key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lset(String key, long index, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lrem(String key, long count, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> smembers(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String spop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sismember(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String srandmember(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrem(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String key, double score, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String key, double score, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrevrank(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zscore(String key, String member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> sort(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> sort(String key, SortingParams sortingParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, String max, String min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByRank(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(String key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(String key, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpushx(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpushx(String key, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pfadd(String key, String... elements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long pfcount(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String set(String key, String arg1, String arg2, String arg3, long arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> srandmember(String key, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long persist(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setbit(String key, long offset, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long strlen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpushx(String key, String... string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpushx(String key, String... string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> blpop(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> brpop(String arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String echo(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long move(String key, int dbIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitcount(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitcount(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, String cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<String> sscan(String key, String cursor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanResult<Tuple> zscan(String key, String cursor) {
		return null;
	}

	@Override
	public void init() {
		getRedis().init();
	}

	@Override
	public void destroy() {
		getRedis().destroy();
	}

	@Override
	public IJedisPool getJedisPool() {
		return this.getRedis().getJedisPool();
	}

	@Override
	public Jedis getResource() {
		return getRedis().getResource();
	}

	@Override
	public boolean append(String key, String value, int seconds) {
		return getRedis().append(key, value, seconds);
	}

	@Override
	public boolean rename(String oldkey, String newkey) {
		return getRedis().rename(oldkey, newkey);
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		return getRedis().setrange(key, offset, value);
	}

	@Override
	public Long setrange(String key, int offset, String value) {
		return getRedis().setrange(key, offset, value);
	}

	@Override
	public Transaction multi() {
		return getRedis().multi();
	}

	@Override
	public boolean flushDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RedisInfo info() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rename(String oldkey, String newkey, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getUsedMemory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long dbSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String set(String key, String value, int seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean flushAll() {
		return getRedis().flushAll();
	}

	@Override
	public boolean set(List<String> keyList, List<String> valueList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean append(List<String> keyList, List<String> valueList, int seconds) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long del(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long del(String key) {
		return getRedis().del(key);
	}

	@Override
	public void returnResource(Jedis jedis) {
		this.getRedis().returnResource(jedis);
	}

	@Override
	public List<String> mget(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zinterstore(String dstkey, String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zinterstore(String dstkey, ZParams params, String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zunionstore(String dstkey, String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zunionstore(String dstkey, ZParams params, String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, long field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(String key, long field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(String key, long field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, double score, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zscore(String key, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrem(String key, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zunionStoreInJava(String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zunionStoreByScoreInJava(double min, double max, String... sets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalsha(String script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eval(String script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eval(String script, int keyCount, String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalsha(String sha1, List<String> keys, List<String> args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalsha(String sha1, int keyCount, String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String evalReturnSha(String script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evalAssertSha(String sha, String script) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String bgrewriteaof() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String bgsave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long publish(String channel, String message) {
		return this.getRedis().publish(channel, message);
	}

	@Override
	public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		this.getRedis().subscribe(jedisPubSub, channels);
	}

	@Override
	public Set<String> sdiff(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(String key, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(String key, long member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sdiffstore(String dstkey, String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String randomKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pexpire(String key, long milliseconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pexpireAt(String key, long millisecondsTimestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double incrByFloat(String key, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> spop(String key, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zlexcount(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByLex(String key, String min, String max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> blpop(int timeout, String key) {
		return this.getRedis().blpop(timeout, key);
	}

	@Override
	public List<String> brpop(int timeout, String key) {
		return this.getRedis().brpop(timeout, key);
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
