package io.leopard.schema;

import io.leopard.data.nosql.NosqlMysqlImpl;
import io.leopard.data4j.memdb.MemdbRsyncImpl;
import io.leopard.jdbc.JdbcDataSource;
import io.leopard.jdbc.JdbcMysqlImpl;
import io.leopard.jdbc.MysqlDsnDataSource;
import io.leopard.memcache.MemcacheRedisImpl;
import io.leopard.redis.RedisHashImpl;
import io.leopard.redis.RedisImpl;
import redis.clients.jedis.JedisPoolConfig;

public class DataSourceManager {

	public static Class<?> findClass(Class<?> clazz) {
		return clazz;
	}

	public static Class<?> getJdbcMysqlImpl() {
		return findClass(JdbcMysqlImpl.class);
	}

	public static Class<?> getJdbcDataSource() {
		return findClass(JdbcDataSource.class);
	}

	public static Class<?> getNosqlMysqlImpl() {
		return findClass(NosqlMysqlImpl.class);
	}

	public static Class<?> getMysqlDsnDataSource() {
		return findClass(MysqlDsnDataSource.class);
	}

	public static Class<?> getMemcacheRedisImpl() {
		return findClass(MemcacheRedisImpl.class);
	}

	public static Class<?> getMemdbRsyncImpl() {
		return findClass(MemdbRsyncImpl.class);
	}

	public static Class<?> getRedisImpl() {
		return findClass(RedisImpl.class);
	}

	public static Class<?> getRedisHashImpl() {
		return findClass(RedisHashImpl.class);
	}

	public static Class<?> getJedisPoolConfig() {
		return findClass(JedisPoolConfig.class);
	}
}
