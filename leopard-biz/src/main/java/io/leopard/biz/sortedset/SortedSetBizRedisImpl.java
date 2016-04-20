package io.leopard.biz.sortedset;

import io.leopard.redis.Redis;

import java.util.Set;

import redis.clients.jedis.Tuple;

public class SortedSetBizRedisImpl implements SortedSetBiz {

	private Redis redis;

	private String key;

	public SortedSetBizRedisImpl(Redis redis, String key) {
		this.redis = redis;
		this.key = key;
	}

	@Override
	public boolean zadd(String element, double score) {
		// System.err.println("zadd " + element + " score:" + score);
		redis.zadd(key, score, element);
		// System.err.println("zadd " + element + " set:" + this.listAll());
		return true;
	}

	@Override
	public Double zscore(String element) {
		Double result = redis.zscore(key, element);
		return result;
	}

	@Override
	public boolean zrem(String element) {
		Long result = redis.zrem(key, element);
		return (result != null && result > 0);
		// return NumberUtil.toBool(result);
	}

	@Override
	public Set<Tuple> listAll() {
		return redis.zrangeWithScores(key, 0, -1);
	}
}