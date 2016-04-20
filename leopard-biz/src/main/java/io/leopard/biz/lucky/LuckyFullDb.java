package io.leopard.biz.lucky;

import io.leopard.redis.Redis;

import java.util.Random;
import java.util.Set;

/**
 * 所有的元素列表.
 * 
 * @author 阿海
 *
 */
public class LuckyFullDb {

	protected Redis redis;

	protected String name;

	public LuckyFullDb(Redis redis, String name) {
		this.redis = redis;
		this.name = name;
	}

	private String getKey() {
		return this.name + ":full";
	}

	public boolean add(String member, int weight) {
		String key = this.getKey();
		redis.zadd(key, weight, member);
		return true;
	}

	public String random() {
		String key = this.getKey();
		int count = this.count();
		if (count <= 0) {
			return null;
		}
		// Random random = new Random();
		int rand = new Random().nextInt(count);
		// int rand = NumberUtil.random(count);
		Set<String> set = redis.zrange(key, rand, rand + 1);
		if (set == null || set.isEmpty()) {
			return null;
		}
		return set.iterator().next();
	}

	public int count() {
		String key = this.getKey();
		Long num = this.redis.zcard(key);
		// return NumberUtil.toInt(num);
		if (num == null) {
			return 0;
		}
		else {
			return num.intValue();
		}
	}

	public boolean clean() {
		String key = this.getKey();
		Long num = this.redis.del(key);
		return (num != null && num > 0);
		// return NumberUtil.toBool(num);
	}

	public int getWeight(String member) {
		String key = this.getKey();
		Double score = this.redis.zscore(key, member);
		if (score == null) {
			return -1;
		}
		return score.intValue();
	}

	public boolean delete(String member) {
		String key = this.getKey();
		this.redis.zrem(key, member);
		return true;
	}

}
