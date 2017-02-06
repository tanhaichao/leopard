package io.leopard.data4j.memdb;

public interface Serialize {

	String serialize(Object obj);

	<T> T toObject(String json, Class<T> clazz);
}
