package io.leopard.redis;

import io.leopard.redis.Redis;
import io.leopard.redis.RedisAllImpl;
import io.leopard.redis.RedisMemoryImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.ZParams;

public class RedisAllImplTest {
	RedisMemoryImpl redisMemoryImpl = new RedisMemoryImpl();
	protected RedisAllImpl redis = newInstance();
	protected final String key = "key";

	public RedisAllImpl newInstance() {
		RedisAllImpl redisAllImpl = new RedisAllImpl();

		redisAllImpl.redisList = new Redis[] { redisMemoryImpl };
		return redisAllImpl;
	}

	@Before
	public void before() {
		redis.del("key");
	}

	@Test
	public void setServerList() {
		redis.setServerList(new String[] { "server" });
	}

	@Test
	public void rename() {
		try {
			redis.rename("oldkey", "newkey");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.rename("oldkey", "newkey", 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void sdiff() {
		try {
			redis.sdiff("set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void subscribe() {
		try {
			redis.subscribe(null, "channel1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void psubscribe() {
		try {
			redis.psubscribe(null, "patterns");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void publish() {
		try {
			redis.publish("channel", "message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getJedisPool() {
		try {
			redis.getJedisPool();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void save() {
		try {
			redis.save();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void bgsave() {
		try {
			redis.bgsave();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void bgrewriteaof() {
		try {
			redis.bgrewriteaof();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void evalAssertSha() {
		try {
			redis.evalAssertSha("sha", "script");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void evalReturnSha() {
		try {
			redis.evalReturnSha("script");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void evalsha() {
		try {
			redis.evalsha("script");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.evalsha("sha1", 1, "params");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.evalsha("sha1", (List<String>) null, (List<String>) null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void eval() {
		try {
			redis.eval("script");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.eval("script", 1, "params");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zunionStoreInJava() {
		try {
			redis.zunionStoreInJava("set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zunionStoreByScoreInJava() {
		try {
			redis.zunionStoreByScoreInJava(1, 2, "set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getResource() {
		try {
			redis.getResource();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void multi() {
		try {
			redis.multi();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void flushDB() {
		try {
			redis.flushDB();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void info() {
		try {
			redis.info();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getUsedMemory() {
		try {
			redis.getUsedMemory();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void dbSize() {
		try {
			redis.dbSize();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void flushAll() {
		try {
			redis.flushAll();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void append() {
		try {
			redis.append((List<String>) null, (List<String>) null, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.append(key, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.append(key, "value", 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void returnResource() {
		try {
			redis.returnResource(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zinterstore() {
		try {
			redis.zinterstore("dstkey", "set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zinterstore("dstkey", (ZParams) null, "set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void keys() {
		try {
			redis.keys("pattern");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zunionstore() {
		try {
			redis.zunionstore("dstkey", "set1", "set2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getServerInfo() {
		try {
			redis.getServerInfo();
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void set() {
		try {
			redis.set(key, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.set(key, "value", 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.set((List<String>) null, (List<String>) null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getRedisList() {
		redis.redisList = null;
		Assert.assertNull(redis.getRedisList());
	}

	@Test
	public void init() {
		redis.init();
	}

	@Test
	public void destroy() {
		redis.destroy();
	}

	@Test
	public void expire() {
		try {
			redis.expire(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void ttl() {
		try {
			redis.ttl(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void incr() {
		try {
			redis.incr(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void get() {
		try {
			redis.get(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getSet() {
		try {
			redis.getSet(key, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zcard() {
		try {
			redis.zcard(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrevrangeWithScores() {
		try {
			redis.zrevrangeWithScores(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrevrange() {
		try {
			redis.zrevrange(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrange() {
		try {
			redis.zrange(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zadd() {
		try {
			redis.zadd(key, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zadd(key, 1, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}

		redis.zadd(key, 1, 1);

	}

	@Test
	public void srem() {
		try {
			redis.srem(key, "member1", "member2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void exists() {
		try {
			redis.exists(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void type() {
		try {
			redis.type(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void expireAt() {
		try {
			redis.expireAt(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void setbit() {
		try {
			redis.setbit(key, 1, true);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getbit() {
		try {
			redis.getbit(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void getrange() {
		try {
			redis.getrange(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void setnx() {
		try {
			redis.setnx(key, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void setex() {
		try {
			redis.setex(key, 1, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void decrBy() {
		try {
			redis.decrBy(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void decr() {
		try {
			redis.decr(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void incrBy() {
		try {
			redis.incrBy(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void substr() {
		try {
			redis.substr(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hset() {
		try {
			redis.hset(key, 1, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.hset(key, "field", "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hget() {
		try {
			redis.hget(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.hget(key, "field");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hsetnx() {
		try {
			redis.hsetnx(key, "field", "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hmset() {
		try {
			redis.hmset(key, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hmget() {
		try {
			redis.hmget(key, "field1", "field2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hincrBy() {
		try {
			redis.hincrBy(key, "field", 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hexists() {
		try {
			redis.hexists(key, "field");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hdel() {
		try {
			redis.hdel(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.hdel(key, "field1", "field2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hlen() {
		try {
			redis.hlen(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hkeys() {
		try {
			redis.hkeys(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hvals() {
		try {
			redis.hvals(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void hgetAll() {
		try {
			redis.hgetAll(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void rpush() {
		try {
			redis.rpush(key, "str1", "str2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lpush() {
		try {
			redis.lpush(key, "str1", "str2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void llen() {
		try {
			redis.llen(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lrange() {
		try {
			redis.lrange(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void ltrim() {
		try {
			redis.ltrim(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lindex() {
		try {
			redis.lindex(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lset() {
		try {
			redis.lset(key, 1, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lrem() {
		try {
			redis.lrem(key, 0, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lpop() {
		try {
			redis.lpop(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void rpop() {
		try {
			redis.rpop(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void smembers() {
		try {
			redis.smembers(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void spop() {
		try {
			redis.spop(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void scard() {
		try {
			redis.scard(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void sismember() {
		try {
			redis.sismember(key, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void srandmember() {
		try {
			redis.srandmember(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrem() {
		try {
			redis.zrem(key, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrem(key, "member1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zincrby() {
		try {
			redis.zincrby(key, 1, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrank() {
		try {
			redis.zrank(key, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrevrank() {
		try {
			redis.zrevrank(key, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrangeWithScores() {
		try {
			redis.zrangeWithScores(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zscore() {
		try {
			redis.zscore(key, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void sort() {
		try {
			redis.sort(key);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.sort(key, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zcount() {
		try {
			redis.zcount(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zcount(key, "0", "1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrangeByScore() {
		try {
			redis.zrangeByScore(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScore(key, "0", "1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScore(key, 0, 1, 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScore(key, "0", "1", 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrevrangeByScore() {
		try {
			redis.zrevrangeByScore(key, 1, 0);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScore(key, "1", "0");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScore(key, 1, 0, 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScore(key, "1", "0", 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrangeByScoreWithScores() {

		try {
			redis.zrangeByScoreWithScores(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScoreWithScores(key, "0", "1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScoreWithScores(key, 0, 1, 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrangeByScoreWithScores(key, "0", "1", 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zrevrangeByScoreWithScores() {
		try {
			redis.zrevrangeByScoreWithScores(key, 1, 0);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScoreWithScores(key, "1", "0");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScoreWithScores(key, 1, 0, 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.zrevrangeByScoreWithScores(key, "1", "0", 0, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zremrangeByRank() {
		try {
			redis.zremrangeByRank(key, 0, 1);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void zremrangeByScore() {

		redis.zremrangeByScore(key, 0, 1);

		try {
			redis.zremrangeByScore(key, "0", "1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void linsert() {
		try {
			redis.linsert(key, null, "pivot", "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void lpushx() {
		try {
			redis.lpushx(key, "string");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void rpushx() {
		try {
			redis.rpushx(key, "string");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void setrange() {
		try {
			redis.setrange(key, 1, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
		try {
			redis.setrange(key, 1L, "value");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void sadd() {
		try {
			redis.sadd(key, "member");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void del() {

		redis.del("key");

		try {
			redis.del("key1", "key2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void mget() {
		try {
			redis.mget("key1", "key2");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	// @Test
	// public void zadd2() {
	// Map<String, Double> scoreMembers = new HashMap<String, Double>();
	// scoreMembers.put("value", 1D);
	// redis.zadd2(key, scoreMembers);
	// }
}