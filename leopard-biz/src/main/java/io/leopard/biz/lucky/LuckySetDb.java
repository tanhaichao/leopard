package io.leopard.biz.lucky;

import io.leopard.redis.Redis;

/**
 * 子集合(按权重划分)
 * 
 * @author 阿海
 *
 */
public class LuckySetDb {
	protected Redis redis;
	protected String name;
	protected int weight;

	public LuckySetDb(Redis redis, String name, int weight) {
		this.redis = redis;
		this.name = name;
		this.weight = weight;
	}

	private String getKey() {
		return this.name + ":" + weight;
	}

	public boolean add(String member) {
		String key = this.getKey();
		redis.sadd(key, member);
		return true;
	}

	public boolean exist(String member) {
		String key = this.getKey();
		Boolean exist = redis.sismember(key, member);
		return exist;
	}

	public boolean clean() {
		String key = this.getKey();
		Long num = this.redis.del(key);
		// return NumberUtil.toBool(num);
		return (num != null && num > 0);
	}

	public String random() {
		String key = this.getKey();
		return redis.srandmember(key);
	}

	public int count() {
		String key = this.getKey();
		Long num = this.redis.scard(key);
		if (num == null) {
			return 0;
		}
		else {
			return num.intValue();
		}
	}

	public boolean delete(String member) {
		String key = this.getKey();
		redis.srem(key, member);
		return true;
	}

}
