package io.leopard.data4j.memdb;

import io.leopard.redis.Redis;

public class MemdbRsyncServiceRedisImpl implements MemdbRsyncService {

	private final Redis redis;

	private final String channel;

	private final String sender;

	private final QueueListener queueListener;

	public MemdbRsyncServiceRedisImpl(Redis redis, String channel, MemdbRsyncQueue memdbRsyncQueue) {
		this.redis = redis;
		this.channel = channel;
		this.sender = Integer.toString(redis.hashCode());
		queueListener = new QueueListener(memdbRsyncQueue, sender);
	}

	@Override
	public boolean add(String type, String key, String value, boolean isMySelf) {
		QueueBean queueBean = new QueueBean();
		queueBean.setType(type);
		queueBean.setKey(key);
		queueBean.setValue(value);
		queueBean.setSender(sender);
		redis.publish(channel, SerializeImpl.getInstance().serialize(queueBean));
		return true;
	}

	@Override
	public void init() {
		new Thread() {
			@Override
			public void run() {
				redis.subscribe(queueListener, channel);
			};
		}.start();
	}

	@Override
	public void destroy() {

	}

}
