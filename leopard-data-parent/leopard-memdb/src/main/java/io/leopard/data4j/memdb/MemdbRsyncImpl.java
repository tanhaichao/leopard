package io.leopard.data4j.memdb;

import io.leopard.redis.Redis;

/**
 * 多机同步实现.
 * 
 * @author 阿海
 * 
 * @param <KEYTYPE>
 */
public class MemdbRsyncImpl extends MemdbLruImpl implements MemdbRsyncQueue {

	protected MemdbRsyncService memdbRsyncService;

	private Redis redis;
	private String channel = "memdb_queue:default";

	public MemdbRsyncImpl(int maxSize) {
		super(maxSize);
	}

	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void init() {
		// System.err.println("init");
		memdbRsyncService = new MemdbRsyncServiceRedisImpl(redis, channel, this);
		// System.err.println("init:1");
		memdbRsyncService.init();
		// System.err.println("init:2");
	}

	public void destroy() {
		memdbRsyncService.destroy();
	}

	@Override
	public boolean set(String key, String value) {
		boolean success = super.set(key, value);
		if (true) {
			memdbRsyncService.add(MemdbRsyncService.TYPE_SET, key, value, false);
		}
		return success;
	}

	@Override
	public boolean remove(String key) {
		boolean success = super.remove(key);
		if (true) {
			memdbRsyncService.add(MemdbRsyncService.TYPE_REMOVE, key, null, false);
		}
		return success;
	}

	@Override
	public boolean add(String type, String key, String value, boolean isMySelf) {
		// System.out.println("add type:" + type + " key:" + key + " value:" +
		// value);
		if (MemdbRsyncQueue.TYPE_SET.equals(type)) {
			super.set(key, value);
			return true;
		}
		else if (MemdbRsyncQueue.TYPE_REMOVE.equals(type)) {
			super.remove(key);
			return true;
		}
		else {
			IllegalArgumentException e = new IllegalArgumentException("未知消息类型[" + type + " key:" + key + "].");
			logger.error(e.getMessage(), e);
			return false;
		}

	}

}
