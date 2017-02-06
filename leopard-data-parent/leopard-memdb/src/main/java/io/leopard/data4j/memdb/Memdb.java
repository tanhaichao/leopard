package io.leopard.data4j.memdb;

public interface Memdb {
	boolean set(String key, String value);

	String get(String key);

	<T> T get(String key, Class<T> clazz);

	boolean remove(String key);

	// List<String> mget(Set<String> keySet);
	//
	// List<String> mget(String... fields);

	long dbSize();
}
