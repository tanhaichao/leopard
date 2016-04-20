package io.leopard.data.cacher;

import io.leopard.burrow.lang.SynchronizedLRUMap;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 内存缓存实现.
 * 
 * @author 阿海
 * 
 */
public abstract class CacherMemoryImpl<KEYTYPE, VALUETYPE> implements Cacher<KEYTYPE, VALUETYPE> {
	protected Log logger = LogFactory.getLog(this.getClass());

	// private Expiry expiry;

	private int interval = 60 * 60;// 缓存时效间隔时间

	// private int keyCount = 1;

	// 当前缓存的数据.
	// private final Map<String, VALUETYPE> data = new LinkedHashMap<String,
	// VALUETYPE>();
	private final Map<String, VALUETYPE> data;

	public CacherMemoryImpl(Expiry expiry) {
		this(expiry, 1);
	}

	public CacherMemoryImpl(Expiry expiry, int keyCount) {
		// this.expiry = expiry;
		this.interval = expiry.getSeconds();
		// this.keyCount = keyCount;
		data = new SynchronizedLRUMap<String, VALUETYPE>(10, keyCount * 2);// 1个存当前数据，1个存上一次的数据
	}

	public abstract VALUETYPE load(KEYTYPE key);

	/**
	 * 获取缓存key.
	 * 
	 * @param key
	 * @return
	 */
	protected String getCacheKey(KEYTYPE key, int slot) {
		if (key == null) {
			return Integer.toString(slot);
		}
		String cacheKey = slot + ":" + key;
		return cacheKey;
	}

	/**
	 * 获取时间段.
	 * 
	 * @param time
	 * @param interval
	 * @return
	 */
	protected int getSlot(long time, int interval) {
		int seconds = (int) (time / 1000L);
		int slot = seconds / interval;// 时间段
		return slot;
	}

	private VALUETYPE rsync(KEYTYPE key, String cacheKey) {
		VALUETYPE value = this.data.get(cacheKey);
		if (value != null) {
			return value;
		}
		VALUETYPE value2 = this.load(key);
		// 删除过期数据
		// this.data.clear();
		// System.out.println("cacheKey:" + cacheKey + " value2:" + value2);
		this.data.put(cacheKey, value2);
		return value2;
	}

	private synchronized VALUETYPE rsyncing(KEYTYPE key, String cacheKey) {
		try {
			isRsyncing = true;
			return rsync(key, cacheKey);
		}
		finally {
			isRsyncing = false;
		}
	}

	private boolean isRsyncing = false;// 是否正在同步

	protected VALUETYPE getPrev(KEYTYPE key, int slot) {
		String cacheKey = getCacheKey(key, slot - 1);
		VALUETYPE value = this.data.get(cacheKey);// 获取上一次的数据
		if (value == null) {
			value = this.rsyncing(key, cacheKey);
		}
		logger.warn("获取上一时间段的数据[" + cacheKey + "]");
		return value;
	}

	@Override
	public VALUETYPE get(KEYTYPE key) {
		// AssertUtil.assertNotNull(key, "参数key不能为null.");
		int slot = getSlot(System.currentTimeMillis(), interval);
		String cacheKey = getCacheKey(key, slot);
		// System.out.println("cacheKey:" + cacheKey);
		VALUETYPE value = this.data.get(cacheKey);
		if (value == null) {
			if (isRsyncing) {
				value = this.getPrev(key, slot);
				// cacheKey = getCacheKey(key, slot - 1);
				// value = this.data.get(cacheKey);// 获取上一次的数据
				// if (value == null) {
				// value = this.rsyncing(key, cacheKey);
				// }
				// logger.warn("获取上一时间段的数据[" + cacheKey + "]");
			}
			else {
				value = this.rsyncing(key, cacheKey);
			}
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
