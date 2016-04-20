package io.leopard.biz.sortedset;

import io.leopard.data4j.pubsub.IPubSub;
import io.leopard.data4j.pubsub.Publisher;
import io.leopard.redis.Redis;

import java.util.Set;

import redis.clients.jedis.Tuple;

public class SortedSetBizImpl implements SortedSetBiz, IPubSub {

	protected static final String SPLIT = ",,,";
	private SortedSetBizRedisImpl sortedSetBizRedisImpl;
	private SortedSetBizMemoryImpl sortedSetBizMemoryImpl;

	public SortedSetBizImpl(Redis redis, String key) {
		this.sortedSetBizRedisImpl = new SortedSetBizRedisImpl(redis, key);
		this.sortedSetBizMemoryImpl = new SortedSetBizMemoryImpl();
		Publisher.listen(this, redis);
		this.load();
	}

	public SortedSetBizRedisImpl getSortedSetBizRedisImpl() {
		return sortedSetBizRedisImpl;
	}

	public SortedSetBizMemoryImpl getSortedSetBizMemoryImpl() {
		return sortedSetBizMemoryImpl;
	}

	@Override
	public boolean zadd(String element, double score) {
		sortedSetBizMemoryImpl.zadd(element, score);
		boolean success = sortedSetBizRedisImpl.zadd(element, score);
		String message = element + SPLIT + score;
		Publisher.publish(this, message);
		return success;
	}

	@Override
	public Double zscore(String element) {
		return sortedSetBizMemoryImpl.zscore(element);
	}

	@Override
	public boolean zrem(String element) {
		boolean success = sortedSetBizRedisImpl.zrem(element);
		sortedSetBizMemoryImpl.zrem(element);
		return success;
	}

	protected void load() {
		Set<Tuple> set = this.sortedSetBizRedisImpl.listAll();
		// System.err.println("load set:" + set);
		if (set == null || set.isEmpty()) {
			return;
		}
		for (Tuple tuple : set) {
			String element = tuple.getElement();
			double score = tuple.getScore();
			this.sortedSetBizMemoryImpl.zadd(element, score);
		}
	}

	@Override
	public Set<Tuple> listAll() {
		return this.sortedSetBizMemoryImpl.listAll();
	}

	@Override
	public void subscribe(String message, boolean isMySelf) {
		if (isMySelf) {
			return;
		}

		String[] list = message.split(SPLIT);
		String element = list[0];
		double score = Double.parseDouble(list[1]);
		this.sortedSetBizMemoryImpl.zadd(element, score);
	}

}
