package io.leopard.redis.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisStringImpl implements IRedisString {
	private Map<String, String> data = new ConcurrentHashMap<String, String>();
	private Map<String, Long> expire = new ConcurrentHashMap<String, Long>();

	@Override
	public Long setnx(String key, String value) {
		if (this.exists(key)) {
			return 0L;
		}
		this.set(key, value);
		return 1L;
	}

	@Override
	public String set(String key, String value) {
		int seconds = 60 * 60 * 24 * 30;
		return this.setex(key, seconds, value);
	}

	@Override
	public String setex(String key, int seconds, String value) {
		data.put(key, value);
		this.expire(key, seconds);
		return value;
	}

	@Override
	public String get(String key) {
		String value = this.data.get(key);
		Long expireTime = this.expire.get(key);
		if (expireTime == null || expireTime < System.currentTimeMillis()) {
			// 记录已过期
			return null;
		}
		return value;
	}

	@Override
	public Long incr(String key) {
		return this.incrBy(key, 1);
	}

	@Override
	public Long decrBy(String key, long integer) {
		String value = this.get(key);
		// long num = NumberUtils.toLong(value);
		long num = value == null ? Long.parseLong(value) : 0;
		num -= integer;
		this.set(key, Long.toString(num));
		return num;
	}

	@Override
	public Long decr(String key) {
		return this.decrBy(key, 1);
	}

	@Override
	public Long incrBy(String key, long integer) {
		String value = this.get(key);
		// long num = NumberUtils.toLong(value);
		long num = value == null ? 0 : Long.parseLong(value);
		num += integer;
		this.set(key, Long.toString(num));
		return num;
	}

	@Override
	public Boolean exists(String key) {
		return data.containsKey(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		if (!this.exists(key)) {
			return 0L;
		}
		long expireTime = System.currentTimeMillis() + seconds * 1000L;
		this.expire.put(key, expireTime);
		return 1L;
	}

	@Override
	public Long del(String key) {
		String value = data.remove(key);
		this.expire.remove(key);
		if (value == null) {
			return 0L;
		}
		else {
			return 1L;
		}
	}

	@Override
	public boolean flushAll() {
		data.clear();
		expire.clear();
		return true;
	}

	@Override
	public Long setrange(String key, long offset, String value) {
		String oldValue = this.get(key);
		oldValue = oldValue == null ? "" : oldValue;
		StringBuilder sb = new StringBuilder(oldValue);
		if (true) {
			int diff = (int) (offset - sb.length());
			for (int i = 0; i < diff; i++) {
				sb.append((char) 2);
			}
		}
		if (true) {
			int start = (int) offset;
			int end = (int) (offset + value.length());
			sb.replace(start, end, value);
		}
		this.set(key, sb.toString());
		return (long) sb.length();
	}
}
