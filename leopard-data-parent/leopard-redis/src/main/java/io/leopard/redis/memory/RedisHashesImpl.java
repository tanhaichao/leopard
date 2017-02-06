package io.leopard.redis.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RedisHashesImpl implements IRedisHashes {
	private Map<String, Map<String, String>> data = new ConcurrentHashMap<String, Map<String, String>>();

	private Map<String, Long> expire = new ConcurrentHashMap<String, Long>();

	protected Map<String, String> getMap(String key) {
		Map<String, String> map = data.get(key);
		if (map == null) {
			map = new ConcurrentHashMap<String, String>();
			data.put(key, map);
		}
		return map;
	}

	@Override
	public Long hset(String key, String field, String value) {
		boolean exists = this.hexists(key, field);
		this.getMap(key).put(field, value);
		return (long) (exists ? 0 : 1);
	}

	@Override
	public String hget(String key, String field) {
		return this.getMap(key).get(field);
	}

	@Override
	public Long hsetnx(String key, String field, String value) {
		boolean exists = this.hexists(key, field);
		if (exists) {
			return 0L;
		}
		this.getMap(key).put(field, value);
		return 1L;
	}

	@Override
	public String hmset(String key, Map<String, String> hash) {
		Iterator<Entry<String, String>> iterator = hash.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String field = entry.getKey();
			String value = entry.getValue();
			this.hset(key, field, value);
		}
		return "OK";
	}

	@Override
	public List<String> hmget(String key, String... fields) {
		List<String> list = new ArrayList<String>();
		for (String field : fields) {
			list.add(hget(key, field));
		}
		return list;
	}

	@Override
	public Long hincrBy(String key, String field, long value) {
		String oldValue = this.hget(key, field);
		long num = oldValue == null ? Long.parseLong(oldValue) : 0;
		num = num + value;
		this.hset(key, field, Long.toString(num));
		return num;
	}

	@Override
	public Boolean hexists(String key, String field) {
		return this.getMap(key).containsKey(field);
	}

	@Override
	public Long hdel(String key, String... fields) {
		long count = 0;
		for (String field : fields) {
			String value = this.getMap(key).remove(field);
			if (value != null) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Long hlen(String key) {
		return (long) this.getMap(key).size();
	}

	@Override
	public Set<String> hkeys(String key) {
		return this.getMap(key).keySet();
	}

	@Override
	public List<String> hvals(String key) {
		Collection<String> c = this.getMap(key).values();
		List<String> list = new ArrayList<String>();
		for (String value : c) {
			list.add(value);
		}
		return list;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		return this.getMap(key);
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
		Object value = data.remove(key);

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

}
