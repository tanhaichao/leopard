package io.leopard.data.counter;

import io.leopard.redis.Redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;

public class CounterRedisImpl implements Counter {

	private Redis redis;

	public Redis getRedis() {
		return redis;
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public static String getKey(String name, TimeUnit unit, long uid) {
		String key = "counter_" + name + "_";
		if (unit == TimeUnit.DAYS) {
			key += new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		else {
			throw new UnsupportedOperationException("未支持类型[" + unit + "].");
		}
		return key;
	}

	@Override
	public long incr(String name, TimeUnit unit, long uid) {
		String key = getKey(name, unit, uid);
		return redis.hincrBy(key, Long.toString(uid), 1);
	}

	@Override
	public void check(String name, TimeUnit unit, long uid, int max) throws MaxCountException {
		String key = getKey(name, unit, uid);
		String value = this.redis.hget(key, uid);
		long count = NumberUtils.toLong(value);
		if (count >= max) {
			throw new MaxCountException(uid, max);
		}
	}

}
