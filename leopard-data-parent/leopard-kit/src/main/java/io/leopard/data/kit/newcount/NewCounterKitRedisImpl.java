package io.leopard.data.kit.newcount;

import io.leopard.redis.Redis;

public class NewCounterKitRedisImpl implements NewCounterKit {

	private Redis redis;

	private String key;

	public void setKey(String key) {
		this.key = key;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	@Override
	public long incr(String member, int num) {
		Double result = redis.zincrby(key, num, member);
		return result.intValue();
	}

	@Override
	public long count(String member) {
		Double result = redis.zscore(key, member);
		if (result == null) {
			return 0;
		}
		return result.intValue();
	}

	@Override
	public boolean delete(String member) {
		// System.out.println("redis:" + redis);
		Long num = redis.zrem(key, member);
		return num >= 0;
	}
}
