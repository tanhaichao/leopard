package io.leopard.redis.util;

import io.leopard.redis.util.RedisUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import redis.clients.jedis.Tuple;

public class RedisUtilTest {

	@Test
	public void createJedisPool() {
		Assert.assertNotNull(RedisUtil.createJedisPool("server", 10));
	}

	@Test
	public void RedisUtil() {
		new RedisUtil();
	}

	@Test
	public void checkList() {

	}

	@Test
	public void getAggregate() {

	}

	@Test
	public void getWeights() {
	}

	@Test
	public void getDefaultWeights() {
		int[] weights = RedisUtil.getDefaultWeights("key1", "key2");
		Assert.assertEquals(1, weights[0]);
		Assert.assertEquals(1, weights[1]);
	}

	@Test
	public void getFirstScore() {
		Assert.assertNull(RedisUtil.getFirstScore(null));
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		Tuple tuple = new Tuple("element", 1d);
		set.add(tuple);
		Assert.assertEquals(1d, (double) RedisUtil.getFirstScore(set), 0);
	}

	@Test
	public void toEntryList() {
		Assert.assertNull(RedisUtil.toEntryList(null));
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		for (int i = 0; i < 3; i++) {
			Tuple tuple = new Tuple("element" + i, 0d);
			set.add(tuple);
		}

		List<Entry<String, Double>> list = RedisUtil.toEntryList(set);
		Assert.assertEquals(3, list.size());
		Assert.assertEquals("element0", list.get(0).getKey());
		Assert.assertEquals("element1", list.get(1).getKey());
	}

	@Test
	public void printServerInfo() {

	}

	@Test
	public void tupleToString() {
		Set<Tuple> set = new LinkedHashSet<Tuple>();
		for (int i = 0; i < 3; i++) {
			Tuple tuple = new Tuple("element" + i, 0d);
			set.add(tuple);
		}

		Set<String> elements = RedisUtil.tupleToString(set);
		Assert.assertEquals("[element0, element1, element2]", elements.toString());
	}

	@Test
	public void tupleToScores() {

	}

}