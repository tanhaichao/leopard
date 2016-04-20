package io.leopard.data.nosql;

import io.leopard.jdbc.Jdbc;

/**
 * 关系型数据库简化成key/value操作.
 * 
 * @author 阿海
 *
 */
public interface Nosql {

	<T> T get(Class<T> elementType, Field... keys);

	<T> T get(Class<T> elementType, String key1, String value1);

	<T> T get(String tableName, Class<T> elementType, Field... keys);

	<T> T get(String tableName, Class<T> elementType, String key1, String value1);

	String getString(String tableName, String fieldName, Field... keys);

	boolean delete(String tableName, Field... keys);

	boolean delete(String tableName, String key1, String value1);

	boolean delete(Field... keys);

	boolean delete(String key1, String value1);

	Jdbc jdbc();
}
