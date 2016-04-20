package io.leopard.data.alldb;

import io.leopard.jdbc.Jdbc;
import io.leopard.redis.Redis;

import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

public class AlldbImpl implements Alldb {

	private MysqlImpl mysqlImpl;

	private StringsImpl stringsImpl;
	private HashsImpl hashsImpl;

	private MemdbImpl memdbImpl;

	public MysqlImpl getMysqlImpl() {
		return mysqlImpl;
	}

	public void setMysqlImpl(MysqlImpl mysqlImpl) {
		this.mysqlImpl = mysqlImpl;
	}

	public StringsImpl getStringsImpl() {
		return stringsImpl;
	}

	public void setStringsImpl(StringsImpl stringsImpl) {
		this.stringsImpl = stringsImpl;
	}

	public HashsImpl getHashsImpl() {
		return hashsImpl;
	}

	public void setHashsImpl(HashsImpl hashsImpl) {
		this.hashsImpl = hashsImpl;
	}

	public MemdbImpl getMemdbImpl() {
		return memdbImpl;
	}

	public void setMemdbImpl(MemdbImpl memdbImpl) {
		this.memdbImpl = memdbImpl;
	}

	@Override
	public <T> T get(Class<T> elementType, Object... keys) {
		if (memdbImpl != null) {
			T bean = memdbImpl.get(elementType, keys);
			if (bean != null) {
				return bean;
			}
			bean = getByRedis(elementType, keys);
			if (bean != null) {
				memdbImpl.set(bean, keys);
			}
			return bean;
		}
		T bean = getByRedis(elementType, keys);
		return bean;
	}

	public <T> T getByRedis(Class<T> elementType, Object... keys) {
		// System.err.println("keys:" + StringUtils.join(keys, ","));
		if (stringsImpl != null) {
			return this.getByRedisStrings(elementType, keys);
		}
		else if (hashsImpl != null) {
			return this.getByRedisHashs(elementType, keys);
		}
		return this.mysqlImpl.get(elementType, keys);
	}

	public <T> T getByRedisStrings(Class<T> elementType, Object... keys) {
		T bean = stringsImpl.get(elementType, keys);
		if (bean != null) {
			return bean;
		}
		bean = this.mysqlImpl.get(elementType, keys);
		if (bean != null) {
			stringsImpl.set(bean, keys);
		}
		return bean;
	}

	public <T> T getByRedisHashs(Class<T> elementType, Object... keys) {
		T bean = hashsImpl.get(elementType, keys);
		if (bean != null) {
			return bean;
		}
		bean = this.mysqlImpl.get(elementType, keys);
		if (bean != null) {
			hashsImpl.set(bean, keys);
		}
		return bean;
	}

	@Override
	public boolean delete(Object key) {
		return false;
	}

	@Override
	public boolean delete(Object key, long opuid, Date lmodify) {
		return false;
	}

	@Override
	public Jdbc jdbc() {
		return this.mysqlImpl.getJdbc();
	}

	@Override
	public Redis redis() {
		if (this.stringsImpl != null) {
			return this.stringsImpl.getRedis();
		}
		else {
			return this.hashsImpl.getRedis();
		}
	}

	@Override
	public Memdb memdb() {
		throw new NotImplementedException();
	}

}
