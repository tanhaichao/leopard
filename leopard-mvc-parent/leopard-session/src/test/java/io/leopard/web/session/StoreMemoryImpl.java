package io.leopard.web.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存实现，紧测试用.
 * 
 * @author 阿海
 *
 */
public class StoreMemoryImpl implements IStore {

	private Map<String, String> data = new ConcurrentHashMap<String, String>(10);

	private static boolean enable = true;

	public static void setEnable(boolean enable) {
		StoreMemoryImpl.enable = enable;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public String get(String key) {
		System.err.println("StroeMemoryImpl get:" + key);
		return data.get(key);
	}

	@Override
	public String set(String key, String value, int seconds) {
		System.err.println("StroeMemoryImpl set:" + key + " " + value);
		data.put(key, value);
		return value;
	}

	@Override
	public Long del(String key) {
		System.err.println("StroeMemoryImpl del:" + key);
		data.remove(key);
		return 1L;
	}

}
