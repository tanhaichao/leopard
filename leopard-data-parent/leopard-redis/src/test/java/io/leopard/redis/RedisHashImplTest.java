package io.leopard.redis;

import io.leopard.redis.DefaultHashType;
import io.leopard.redis.LongHashType;
import io.leopard.redis.Redis;
import io.leopard.redis.RedisHashImpl;
import io.leopard.redis.RedisMemoryImpl;
import io.leopard.redis.StringHashType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;

public class RedisHashImplTest {

	private RedisMemoryImpl redisMemoryImpl = Mockito.spy(new RedisMemoryImpl());
	protected RedisHashImpl redis = newInstance();

	private final String key = "key";

	public RedisHashImpl newInstance() {
		RedisHashImpl redisHashImpl = new RedisHashImpl();
		redisHashImpl.redisList = new Redis[] { redisMemoryImpl };
		return redisHashImpl;
	}

	@Before
	public void before() {
		redis.del("key");
	}

	@Test
	public void setMaxActive() {
		redis.setMaxActive(1);
		Assert.assertEquals(1, redis.maxActive);
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
	public void zrange() {
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 1, "one"));
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 2, "two"));
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 3, "three"));

		Assert.assertEquals("one,two,three", StringUtils.join(this.redis.zrange(key, 0, -1), ","));
	}

	@Test
	public void set() throws InterruptedException {
		this.redis.set(key, "value");
		Assert.assertEquals("value", this.redis.get(key));

		this.redis.set("key1", "value", 1);
		Assert.assertEquals("value", this.redis.get("key1"));
		// ThreadUtil.sleep(1100);
		Thread.sleep(1100);
		Assert.assertNull(this.redis.get("key1"));
	}

	@Test
	public void set2() {
		try {
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			redis.set(list, null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void setnx() {
		this.redis.setnx(key, "value");
		this.redis.setnx(key, "value1");
		Assert.assertEquals("value", this.redis.get(key));

	}

	@Test
	public void setex() {
		this.redis.setex(key, 100, "value");
		this.redis.setex(key, 100, "value1");
		Assert.assertEquals("value1", this.redis.get(key));

	}

	@Test
	public void exists() {
		redis.set(key, "value");
		Assert.assertTrue(redis.exists(key));
	}

	@Test
	public void decr() {
		Assert.assertEquals(-1L, (long) this.redis.decr(key));
		Assert.assertEquals(-2L, (long) this.redis.decr(key));
	}

	@Test
	public void incr() {
		Assert.assertEquals(1L, (long) this.redis.incr(key));
		Assert.assertEquals(2L, (long) this.redis.incr(key));
	}

	@Test
	public void hset() {
		Assert.assertEquals(1L, (long) this.redis.hset(key, "field1", "value"));
		Assert.assertEquals(0L, (long) this.redis.hset(key, "field1", "value2"));
		Assert.assertEquals("value2", this.redis.hget(key, "field1"));
		Assert.assertEquals(1L, (long) this.redis.hset(key, "field2", "value"));

	}

	@Test
	public void hset2() {
		Assert.assertEquals(1L, (long) this.redis.hset(key, 1, "value"));
		Assert.assertEquals("value", this.redis.hget(key, 1));
		Assert.assertEquals(1, this.redis.hdel(key, 1).intValue());
	}

	@Test
	public void hsetnx() {
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value"));
		Assert.assertEquals(0L, (long) this.redis.hsetnx(key, "field1", "value2"));
		Assert.assertEquals("value", this.redis.hget(key, "field1"));

	}

	@Test
	public void hmget() {
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field2", "value2"));

		List<String> list = this.redis.hmget(key, "field1", "field2");

		Assert.assertEquals("value1,value2", StringUtils.join(list, ","));
	}

	@Test
	public void hdel() {
		Assert.assertEquals(0L, (long) this.redis.hdel(key, "field1"));
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redis.hdel(key, "field1"));

	}

	@Test
	public void hkeys() {
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field2", "value2"));
		Set<String> keys = this.redis.hkeys(key);
		Assert.assertEquals("field1,field2", StringUtils.join(keys, ","));

	}

	@Test
	public void hvals() {
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field2", "value2"));
		List<String> values = this.redis.hvals(key);
		Assert.assertEquals("value1,value2", StringUtils.join(values, ","));
	}

	// @Ignore
	@Test
	public void hgetAll() {
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field1", "value1"));
		Assert.assertEquals(1L, (long) this.redis.hsetnx(key, "field2", "value2"));
		Map<String, String> map = this.redis.hgetAll(key);

		Assert.assertEquals(2, map.size());
		Assert.assertEquals("field1,field2", StringUtils.join(map.keySet(), ","));
		Assert.assertEquals("value1,value2", StringUtils.join(map.values(), ","));
	}

	@Test
	public void zrank() {
		this.redis.zadd(key, 1, "one");
		this.redis.zadd(key, 2, "two");
		this.redis.zadd(key, 3, "three");

		Assert.assertEquals(0L, (long) this.redis.zrank(key, "one"));
		Assert.assertEquals(1L, (long) this.redis.zrank(key, "two"));
		Assert.assertEquals(2L, (long) this.redis.zrank(key, "three"));
		Assert.assertNull(this.redis.zrank(key, "four"));
	}

	@Test
	public void zrevrank() {
		this.redis.zadd(key, 1, "one");
		this.redis.zadd(key, 2, "two");
		this.redis.zadd(key, 3, "three");

		Assert.assertEquals(2L, (long) this.redis.zrevrank(key, "one"));
		Assert.assertEquals(1L, (long) this.redis.zrevrank(key, "two"));
		Assert.assertEquals(0L, (long) this.redis.zrevrank(key, "three"));
		Assert.assertNull(this.redis.zrank(key, "four"));
	}

	@Test
	public void zcard() {
		this.redis.zadd(key, 1, "member1");
		this.redis.zadd(key, 1, "member2");
		Assert.assertEquals(2L, (long) this.redis.zcard(key));

	}

	@Test
	public void zrevrangeByScore() {
		this.redis.zadd(key, 1, "one");
		this.redis.zadd(key, 2, "two");
		this.redis.zadd(key, 3, "three");

		Assert.assertEquals("two,one", StringUtils.join(redis.zrevrangeByScore(key, 2, 1, 0, 10), ","));
		Assert.assertEquals("two,one", StringUtils.join(redis.zrevrangeByScore(key, "2", "1", 0, 10), ","));
		Assert.assertEquals("two,one", StringUtils.join(redis.zrevrangeByScore(key, 2, 1), ","));
		Assert.assertEquals("two,one", StringUtils.join(redis.zrevrangeByScore(key, "2", "1"), ","));
	}

	@Test
	public void zscore() {
		this.redis.zscore(key, "member1");
	}

	@Test
	public void lpush() {
		Assert.assertEquals(1L, (long) this.redis.lpush(key, "world"));
		Assert.assertEquals(2L, (long) this.redis.lpush(key, "hello"));

		Assert.assertEquals("hello,world", StringUtils.join(this.redis.lrange(key, 0, -1), ","));
		Assert.assertEquals("hello,world", StringUtils.join(this.redis.lrange(key, 0, 1), ","));
		System.out.println("================");
		Assert.assertEquals("hello", StringUtils.join(this.redis.lrange(key, 0, 0), ","));
		Assert.assertEquals("world", StringUtils.join(this.redis.lrange(key, 1, 1), ","));
	}

	@Test
	public void lrange() {
		Assert.assertEquals(1L, (long) this.redis.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redis.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redis.rpush(key, "three"));

		Assert.assertEquals("one", StringUtils.join(this.redis.lrange(key, 0, 0), ","));
		Assert.assertEquals("one,two,three", StringUtils.join(this.redis.lrange(key, 0, -1), ","));
	}

	@Test
	public void rpush() {
		Assert.assertEquals(1L, (long) this.redis.rpush(key, "hello"));
		Assert.assertEquals(2L, (long) this.redis.rpush(key, "world"));

		Assert.assertEquals("hello,world", StringUtils.join(this.redis.lrange(key, 0, -1), ","));
		Assert.assertEquals("hello,world", StringUtils.join(this.redis.lrange(key, 0, 1), ","));
		Assert.assertEquals("hello", StringUtils.join(this.redis.lrange(key, 0, 0), ","));
		Assert.assertEquals("world", StringUtils.join(this.redis.lrange(key, 1, 1), ","));
	}

	@Test
	public void lpop() {
		Assert.assertEquals(1L, (long) this.redis.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redis.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redis.rpush(key, "three"));

		Assert.assertEquals("one", this.redis.lpop(key));

		Assert.assertEquals("two,three", StringUtils.join(this.redis.lrange(key, 0, -1), ","));
	}

	@Test
	public void rpop() {
		Assert.assertEquals(1L, (long) this.redis.rpush(key, "one"));
		Assert.assertEquals(2L, (long) this.redis.rpush(key, "two"));
		Assert.assertEquals(3L, (long) this.redis.rpush(key, "three"));

		Assert.assertEquals("three", this.redis.rpop(key));

		Assert.assertEquals("one,two", StringUtils.join(this.redis.lrange(key, 0, -1), ","));
	}

	@Test
	public void zrevrange() {
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 1, "one"));
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 2, "two"));
		Assert.assertEquals(1L, (long) this.redis.zadd(key, 3, "three"));

		Assert.assertEquals("three,two,one", StringUtils.join(this.redis.zrevrange(key, 0, -1), ","));
	}

	@Test
	public void setrange() {
		this.redis.set(key, "Hello World");
		Assert.assertEquals(11L, (long) this.redis.setrange(key, 6, "Redis"));
		Assert.assertEquals("Hello Redis", this.redis.get(key));
	}

	// @Test
	// public void zinterstore() {
	// redis.zadd("zset1", 1, "one");
	// redis.zadd("zset1", 2, "two");
	// redis.zadd("zset2", 1, "one");
	// redis.zadd("zset2", 2, "two");
	// redis.zadd("zset2", 3, "three");
	//
	// ZParams params = new ZParams().aggregate(ZParams.Aggregate.SUM);
	// params.weights(2, 3);
	// this.redis.zinterstore(key, params, "zset1", "zset2");
	// Set<Tuple> set = this.redis.zrangeWithScores(key, 0, -1);
	//
	// Json.print(set, "set");
	//
	// Assert.assertEquals("one,two",
	// StringUtils.join(RedisUtil.tupleToString(set), ","));
	// Assert.assertEquals("5.0,10.0",
	// StringUtils.join(RedisUtil.tupleToScores(set), ","));
	// }

	@Test
	public void zParams() {
		ZParams zParams = new ZParams().aggregate(ZParams.Aggregate.SUM);
		zParams.weights(1, 1);
		Collection<byte[]> collect = zParams.getParams();
		System.out.println("######################");
		for (byte[] b : collect) {
			System.out.println("collect :" + new String(b));
		}
		System.out.println("######################");
	}

	@Test
	public void hget() {
		Assert.assertNull(this.redis.hget(key, "field"));
		Assert.assertNull(this.redis.hget(key, 1));
	}

	@Test
	public void smembers() {
		Assert.assertEquals(1L, (long) this.redis.sadd(key, "Hello"));
		Assert.assertEquals(1L, (long) this.redis.sadd(key, "World"));
		Assert.assertEquals("World,Hello", StringUtils.join(this.redis.smembers(key), ","));

	}

	@Test
	public void zrem() {
		this.redis.zadd(key, 1, "one");
		this.redis.zadd(key, 2, "two");
		this.redis.zadd(key, 3, "three");

		Assert.assertEquals(1L, this.redis.zrem(key, "one"), 0);
	}

	@Test
	public void hincrBy() {
		redis.hset("key", "field", "1");
		Assert.assertEquals(2L, redis.hincrBy("key", "field", 1), 0);
		Assert.assertEquals(5L, redis.hincrBy("key", "field", 3), 0);
		Assert.assertEquals(2L, redis.hincrBy("key", "field", -3), 0);
		Assert.assertEquals("2", redis.hget("key", "field"));
	}

	// @Test
	// public void sdiff() {
	// redis.del("key1", "key2", "key3");
	// redis.sadd("key1", "a", "b", "c");
	// redis.sadd("key2", "c", "d", "e");
	// // redis.sadd("key3", "c", "a", "g");
	// Set<String> set = redis.sdiff("key1", "key2");
	// System.out.println("set:" + set);
	// Assert.assertEquals(2, set.size());
	// Assert.assertTrue(set.contains("a"));
	// Assert.assertTrue(set.contains("b"));
	// }

	@Test
	public void sadd() {
		redis.del(key);
		Assert.assertEquals(3L, redis.sadd(key, "a", "b", "c").longValue());
		Assert.assertEquals(1L, redis.sadd(key, "a", "b", "c", "d").longValue());
	}

	@Test
	public void get() {
		// AUTO
	}

	@Test
	public void hlen() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "value1");
		map.put("field2", "value2");
		redis.hmset(key, map);

		Assert.assertEquals(2, redis.hlen(key).intValue());

		Assert.assertTrue(redis.hexists(key, "field1"));
		Assert.assertFalse(redis.hexists(key, "field3"));
	}

	@Test
	public void zadd() {
		Assert.assertEquals(1L, (long) redis.zadd(key, 1, "member1"));
		Assert.assertEquals(0L, (long) redis.zadd(key, 1, "member1"));

		Assert.assertEquals(1, redis.zcard(key).intValue());
	}

	@Test
	public void zadd5() {
		Mockito.doReturn(null).when(redisMemoryImpl).zadd(key, (Map<String, Double>) null);
		Assert.assertNull(redis.zadd(key, null));
	}

	@Test
	public void zadd4() {
		Assert.assertEquals(1L, (long) redis.zadd(key, 1, 1));
		Assert.assertEquals(1, redis.zcard(key).intValue());
		redis.zrem(key, 1);
		Assert.assertEquals(0, redis.zcard(key).intValue());
	}

	@Test
	public void zadd3() {
		try {
			redis.zadd(key, (Map<String, Double>) null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void scard() {
		redis.sadd(key, "member1", "member2");
		Assert.assertEquals(2, redis.scard(key).intValue());
	}

	@Test
	public void sismember() {
		redis.sadd(key, "member1", "member2");
		Assert.assertTrue(redis.sismember(key, "member1"));
		Assert.assertFalse(redis.sismember(key, "member3"));
	}

	// @Test
	// public void zadd2() {
	// Map<String, Double> scoreMembers = new LinkedHashMap<String, Double>();
	// scoreMembers.put("member1", 1D);
	// scoreMembers.put("member2", 2D);
	// Assert.assertEquals(2, redis.zadd2(key, scoreMembers).intValue());
	// Assert.assertEquals(2, redis.zcard(key).intValue());
	// }

	@Test
	public void zcount() {
		Map<String, Double> scoreMembers = new LinkedHashMap<String, Double>();
		scoreMembers.put("member1", 1D);
		scoreMembers.put("member2", 2D);
		scoreMembers.put("member3", 3D);
		Assert.assertEquals(3, redis.zadd(key, scoreMembers).intValue());

		Assert.assertEquals(2, redis.zcount(key, 1, 2).intValue());
		Assert.assertEquals(2, redis.zcount(key, "1", "2").intValue());
	}

	@Test
	public void zrangeWithScores() {
		Map<String, Double> scoreMembers = new LinkedHashMap<String, Double>();
		scoreMembers.put("member1", 1D);
		scoreMembers.put("member2", 2D);
		scoreMembers.put("member3", 3D);
		Assert.assertEquals(3, redis.zadd(key, scoreMembers).intValue());

		Set<Tuple> set = redis.zrangeWithScores(key, 0, -1);
		Assert.assertEquals(3, set.size());

		Tuple tuple = set.iterator().next();
		Assert.assertEquals("member1", tuple.getElement());
		Assert.assertEquals(1D, tuple.getScore(), 0);
	}

	@Test
	public void zrevrangeWithScores() {
		Map<String, Double> scoreMembers = new LinkedHashMap<String, Double>();
		scoreMembers.put("member1", 1D);
		scoreMembers.put("member2", 2D);
		scoreMembers.put("member3", 3D);
		Assert.assertEquals(3, redis.zadd(key, scoreMembers).intValue());

		Set<Tuple> set = redis.zrevrangeWithScores(key, 0, -1);
		Assert.assertEquals(3, set.size());

		Tuple tuple = set.iterator().next();
		Assert.assertEquals("member3", tuple.getElement());
		Assert.assertEquals(3D, tuple.getScore(), 0);
	}

	@Test
	public void srandmember() {
		redis.sadd(key, "member1", "member2");
		Assert.assertNotNull(this.redis.srandmember(key));
	}

	@Test
	public void spop() {
		redis.sadd(key, "one");
		redis.sadd(key, "two");
		redis.sadd(key, "three");
		Assert.assertEquals("one", redis.spop(key));
	}

	@Test
	public void lpushx() {
		redis.lpush(key, "World");
		Assert.assertEquals(2, redis.lpushx(key, "Hello").intValue());
		Assert.assertEquals(0, redis.lpushx("myotherlist", "Hello").intValue());
		Assert.assertEquals("[Hello, World]", redis.lrange(key, 0, -1).toString());
		Assert.assertEquals(0, redis.lrange("myotherlist", 0, -1).size());
	}

	@Test
	public void rpushx() {
		redis.rpush(key, "Hello");
		Assert.assertEquals(2, redis.rpushx(key, "World").intValue());
		Assert.assertEquals(0, redis.rpushx("myotherlist", "Hello").intValue());
		Assert.assertEquals("[Hello, World]", redis.lrange(key, 0, -1).toString());
		Assert.assertEquals(0, redis.lrange("myotherlist", 0, -1).size());
	}

	@Test
	public void ltrim() {
		Assert.assertEquals(1, redis.rpush(key, "one").intValue());
		Assert.assertEquals(2, redis.rpush(key, "two").intValue());
		Assert.assertEquals(3, redis.rpush(key, "three").intValue());

		Assert.assertEquals("OK", redis.ltrim(key, 1, -1));

		Assert.assertEquals("[two, three]", redis.lrange(key, 0, -1).toString());
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
		redis.append("key", "Hello");
		redis.append("key", " World", 1);
		Assert.assertEquals("Hello World", redis.get("key"));
		try {
			redis.append((List<String>) null, (List<String>) null, 1);
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
			redis.zinterstore("dstkey", (ZParams) null, "sets");
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
	public void setTimeout() {
		redis.setTimeout(1);
		Assert.assertEquals(1, redis.timeout);
	}

	@Test
	public void setServerList() {
		redis.setServerList(new String[] { "a", "b" });
		// Assert.assertEquals("a,b", StringUtils.join(redis.serverList, ","));

		redis.init();

		Assert.assertEquals(2, redis.getRedisList().length);
		redis.destroy();
	}

	@Test
	public void getHashTypeClassName() {
		Assert.assertEquals(StringHashType.class.getName(), redis.getHashTypeClassName("string"));
		Assert.assertEquals(LongHashType.class.getName(), redis.getHashTypeClassName("long"));
		Assert.assertEquals(DefaultHashType.class.getName(), redis.getHashTypeClassName("default"));
		Assert.assertEquals(DefaultHashType.class.getName(), redis.getHashTypeClassName(""));
		Assert.assertEquals("com.duowan.HashTypeImpl", redis.getHashTypeClassName("com.duowan.HashTypeImpl"));
	}

	@Test
	public void setHashType() {
		redis.setHashType("string");
		Assert.assertTrue(redis.hashType instanceof StringHashType);
	}

	@Test
	public void expire() {
		redis.expire(key, 1);
	}

	@Test
	public void ttl() {
		Mockito.doReturn(1L).when(redisMemoryImpl).ttl(key);
		Assert.assertEquals(1, redis.ttl(key).intValue());
	}

	@Test
	public void getSet() {
		this.redis.incr(key);
		redis.getSet(key, "0");
		Assert.assertEquals("0", redis.get(key));
	}

	@Test
	public void srem() {
		Assert.assertEquals(1, redis.sadd(key, "one").intValue());
		Assert.assertEquals(1, redis.sadd(key, "two").intValue());
		Assert.assertEquals(1, redis.sadd(key, "three").intValue());

		Assert.assertEquals(1, redis.srem(key, "one").intValue());
		Assert.assertEquals(0, redis.srem(key, "four").intValue());
	}

	@Test
	public void type() {
		Mockito.doReturn("type").when(redisMemoryImpl).type(key);
		Assert.assertEquals("type", redis.type(key));
	}

	@Test
	public void expireAt() {
		Mockito.doReturn(1L).when(redisMemoryImpl).expireAt(key, 1);
		Assert.assertEquals(1, redis.expireAt(key, 1).intValue());
	}

	@Test
	public void setbit() {
		Mockito.doReturn(true).when(redisMemoryImpl).setbit(key, 1, true);
		Assert.assertEquals(true, redis.setbit(key, 1, true));
	}

	@Test
	public void getbit() {
		Mockito.doReturn(true).when(redisMemoryImpl).getbit(key, 1);
		Assert.assertEquals(true, redis.getbit(key, 1));

	}

	@Test
	public void getrange() {
		redis.set(key, "This is a string");
		Assert.assertEquals("This", redis.getrange(key, 0, 3));
		Assert.assertEquals("ing", redis.getrange(key, -3, -1));
		Assert.assertEquals("This is a string", redis.getrange(key, 0, -1));
		Assert.assertEquals("string", redis.getrange(key, 10, 100));
	}

	@Test
	public void zrangeByScore() {
		this.redis.zadd(key, 1, "one");
		this.redis.zadd(key, 2, "two");
		this.redis.zadd(key, 3, "three");

		Assert.assertEquals("one,two,three", StringUtils.join(redis.zrangeByScore(key, 0, -1), ","));
		Assert.assertEquals("one,two,three", StringUtils.join(redis.zrangeByScore(key, "0", "-1"), ","));
		Assert.assertEquals("one,two,three", StringUtils.join(redis.zrangeByScore(key, "0", "-1", 0, 10), ","));
		Assert.assertEquals("one,two,three", StringUtils.join(redis.zrangeByScore(key, 0, -1, 0, 10), ","));
	}

	@Test
	public void del() {
		this.redis.set("key1", "value1");
		this.redis.set("key2", "value2");
		this.redis.set("key3", "value3");
		redis.del("key1", "key2", "key3");
		Assert.assertFalse(redis.exists("key1"));
		Assert.assertFalse(redis.exists("key2"));
		Assert.assertFalse(redis.exists("key3"));
	}

	@Test
	public void mget() {
		this.redis.set("key1", "value1");
		this.redis.set("key2", "value2");
		this.redis.set("key3", "value3");

		List<String> list = redis.mget("key1", "key2", "key3");

		Assert.assertEquals("[value1, value2, value3]", list.toString());
	}

	@Test
	public void zremrangeByScore() {
		Assert.assertNull(redis.zremrangeByScore(key, 0, 1));
		Assert.assertNull(redis.zremrangeByScore(key, "0", "1"));
	}

	@Test
	public void zremrangeByRank() {
		Assert.assertNull(redis.zremrangeByRank(key, 0, 1));
	}

	@Test
	public void zrevrangeByScoreWithScores() {
		Assert.assertNull(redis.zrevrangeByScoreWithScores(key, 1, 0));
		Assert.assertNull(redis.zrevrangeByScoreWithScores(key, "1", "0"));
		Assert.assertNull(redis.zrevrangeByScoreWithScores(key, 1, 0, 0, 10));
		Assert.assertNull(redis.zrevrangeByScoreWithScores(key, "1", "0", 0, 10));

	}

	@Test
	public void zrangeByScoreWithScores() {
		Assert.assertNull(redis.zrangeByScoreWithScores(key, 0, 10));
		Assert.assertNull(redis.zrangeByScoreWithScores(key, "0", "10"));
		Assert.assertNull(redis.zrangeByScoreWithScores(key, 0, 10, 0, 10));
		Assert.assertNull(redis.zrangeByScoreWithScores(key, "0", "10", 0, 10));
	}

	@Test
	public void sort() {
		Mockito.doReturn(null).when(redisMemoryImpl).sort(key);
		Assert.assertNull(redis.sort(key));
		Mockito.doReturn(null).when(redisMemoryImpl).sort(key, null);
		Assert.assertNull(redis.sort(key, null));
	}

	@Test
	public void linsert() {
		Mockito.doReturn(1L).when(redisMemoryImpl).linsert(key, null, "pivot", "value");
		Assert.assertEquals(1L, (long) redis.linsert(key, null, "pivot", "value"));
	}

	@Test
	public void decrBy() {
		redis.decrBy(key, 2);
		Assert.assertEquals("-2", redis.get(key));
	}

	@Test
	public void zincrby() {
		Assert.assertEquals(1D, this.redis.zincrby(key, 1, "member1"), 0);
		Assert.assertEquals(2D, this.redis.zincrby(key, 1, "member1"), 0);
	}

	@Test
	public void incrBy() {
		redis.incrBy(key, 2);
		Assert.assertEquals("2", redis.get(key));
	}

	@Test
	public void substr() {
		Mockito.doReturn(null).when(redisMemoryImpl).substr(key, 0, 1);
		Assert.assertNull(redis.substr(key, 0, 1));
	}

	@Test
	public void llen() {
		Assert.assertEquals(1, redis.lpush(key, "World").intValue());
		Assert.assertEquals(2, redis.lpush(key, "Hello").intValue());
		Assert.assertEquals(2, redis.llen(key).intValue());
	}

	@Test
	public void lindex() {
		Assert.assertEquals(1, redis.rpush(key, "World").intValue());
		Assert.assertEquals(2, redis.rpush(key, "Hello").intValue());

		Assert.assertEquals("Hello", redis.lindex(key, 0));
		Assert.assertEquals("World", redis.lindex(key, -1));
		Assert.assertNull(redis.lindex(key, 3));
	}

	@Test
	public void lset() {
		Assert.assertEquals(1, redis.rpush(key, "one").intValue());
		Assert.assertEquals(2, redis.rpush(key, "two").intValue());
		Assert.assertEquals(3, redis.rpush(key, "three").intValue());
		Assert.assertEquals("OK", redis.lset(key, 0, "four"));
		Assert.assertEquals("[four, two, three]", redis.lrange(key, 0, -1).toString());
		Assert.assertEquals("OK", redis.lset(key, -2, "five"));
		Assert.assertEquals("[four, five, three]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void lrem2() {
		Assert.assertEquals(1, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(2, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(3, redis.rpush(key, "foo").intValue());
		Assert.assertEquals(4, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(2, redis.lrem(key, -2, "hello").intValue());
		Assert.assertEquals("[hello, foo]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void lrem1() {
		Assert.assertEquals(1, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(2, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(3, redis.rpush(key, "foo").intValue());
		Assert.assertEquals(4, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(2, redis.lrem(key, 2, "hello").intValue());

		Assert.assertEquals("[foo, hello]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void lrem0() {
		Assert.assertEquals(1, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(2, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(3, redis.rpush(key, "foo").intValue());
		Assert.assertEquals(4, redis.rpush(key, "hello").intValue());
		Assert.assertEquals(3, redis.lrem(key, 0, "hello").intValue());

		Assert.assertEquals("[foo]", redis.lrange(key, 0, -1).toString());
	}

	@Test
	public void lrem() {
		// AUTO
	}

	// @Test
	// public void zincrby() {
	// Assert.assertEquals(1D, this.redis.zincrby(key, 1, "member1"), 0);
	// Assert.assertEquals(2D, this.redis.zincrby(key, 1, "member1"), 0);
	//
	// }
}