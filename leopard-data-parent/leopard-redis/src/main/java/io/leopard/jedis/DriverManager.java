package io.leopard.jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public class DriverManager {

	private static List<Driver> list = new ArrayList<Driver>();

	static {
		loadInitialDrivers();
	}

	private static void loadInitialDrivers() {
		ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
		Iterator<Driver> driversIterator = loadedDrivers.iterator();
		while (driversIterator.hasNext()) {
			Driver driver = driversIterator.next();
			list.add(driver);
		}
	}

	// TODO 这里要改成LUR.
	private static Map<Integer, redis.clients.jedis.Jedis> cache = new ConcurrentHashMap<Integer, redis.clients.jedis.Jedis>();

	public static redis.clients.jedis.Jedis wrapper(redis.clients.jedis.Jedis jedis) {
		int key = jedis.hashCode();
		{
			redis.clients.jedis.Jedis jedis2 = cache.get(key);
			if (jedis2 != null) {
				return jedis2;
			}
		}
		for (Driver driver : list) {
			jedis = driver.connect(jedis);
		}
		cache.put(key, jedis);
		return jedis;
	}

}
