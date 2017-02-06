package io.leopard.memcache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemcacheMemoryImpl implements Memcache {

	private Map<String, String> cache = new ConcurrentHashMap<String, String>();

	@Override
	public boolean remove(String key) {
		Object value = cache.remove(key);
		return (value != null);
	}

	@Override
	public boolean put(String key, String value) {
		// AssertUtil.assertNotNull(key, "key参数不能为null.");
		if (key == null) {
			throw new NullPointerException("key参数不能为null.");
		}
		cache.put(key, value);
		return true;
	}

	@Override
	public boolean put(String key, String value, int seconds) {
		cache.put(key, value);
		return true;
	}

	@Override
	public boolean put(String key, int value) {
		return this.put(key, Integer.toString(value));
	}

	@Override
	public boolean put(String key, int value, int seconds) {
		return this.put(key, Integer.toString(value), seconds);
	}

	@Override
	public String get(String key) {
		return cache.get(key);
	}

	// @Override
	// public <BEAN> BEAN get(String key, Class<BEAN> clazz) {
	// String json = this.get(key);
	// return Json.toObject(json, clazz);
	// }

	@Override
	public List<String> mget(String[] keys) {
		throw new UnsupportedOperationException("Not Implemented.");
	}

	// @Override
	// public <BEAN> List<BEAN> mget(String[] keys, Class<BEAN> clazz) {
	// throw new NotImplementedException();
	// }

	// @Override
	// public boolean putObject(String key, Object obj) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public boolean putObject(String key, Object obj, int seconds) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public Object getObject(String key) {
	// throw new NotImplementedException();
	// }

	@Override
	public boolean flushAll() {
		throw new UnsupportedOperationException("Not Implemented.");
	}

	@Override
	public long incr(String key) {
		if (key == null) {
			throw new NullPointerException("key参数不能为null.");
		}
		// AssertUtil.assertNotNull(key, "key参数不能为null.");
		String value = this.get(key);
		long num;
		if (value == null) {
			num = 0;
		}
		else {
			num = Long.parseLong(value);
		}
		num++;
		this.put(key, Long.toString(num));
		return num;
		// throw new NotImplementedException();
	}

	@Override
	public long addOrIncr(String key, long num) {
		throw new UnsupportedOperationException("Not Implemented.");
	}

	@Override
	public int getInt(String key) {
		throw new UnsupportedOperationException("Not Implemented.");
	}

	@Override
	public boolean add(String key, String value) {
		return this.put(key, value);
	}

	@Override
	public boolean add(String key, String value, int seconds) {
		return this.put(key, value, seconds);
	}

}
