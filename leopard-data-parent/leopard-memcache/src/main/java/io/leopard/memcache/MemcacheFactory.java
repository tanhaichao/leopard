package io.leopard.memcache;

public class MemcacheFactory {
	// public static MemcacheImpl create(String server) {
	// MemcacheImpl memcacheImpl = new MemcacheImpl();
	// memcacheImpl.setServer(server);
	// return memcacheImpl;
	// }

	public static Memcache createMemoryImpl() {
		return new MemcacheMemoryImpl();
	}
}
