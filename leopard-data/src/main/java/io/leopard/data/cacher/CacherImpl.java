package io.leopard.data.cacher;

import io.leopard.burrow.lang.SynchronizedLRUMap;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CacherImpl<KEYTYPE, VALUETYPE> implements Cacher<KEYTYPE, VALUETYPE> {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected int interval = 60 * 60;// 缓存时效间隔时间

	private final Map<KEYTYPE, VALUETYPE> data;

	public CacherImpl(Expiry expiry) {
		this(expiry, 1);
	}

	public CacherImpl(Expiry expiry, int keyCount) {
		// this.expiry = expiry;
		this.interval = expiry.getSeconds();
		// this.keyCount = keyCount;
		data = new SynchronizedLRUMap<KEYTYPE, VALUETYPE>(10, keyCount * 2);// 1个存当前数据，1个存上一次的数据
	}

	public abstract VALUETYPE load(KEYTYPE key);

	@Override
	public VALUETYPE get(KEYTYPE key) {
		VALUETYPE value = this.data.get(key);
		if (value == null) {
			value = this.rsyncing(key);
		}
		return value;
	}

	protected synchronized VALUETYPE rsyncing(KEYTYPE key) {
		VALUETYPE value = this.data.get(key);
		if (value == null) {
			value = this.load(key);
			this.data.put(key, value);
		}
		return value;
	}

	@Override
	public boolean remove(KEYTYPE key) {
		VALUETYPE value = this.data.remove(key);
		System.out.println("value:" + value);
		return (value != null);
	}

}
