package io.leopard.data4j.memdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.redis.Redis;

public class MemdbRsyncServiceRedisImpl implements MemdbRsyncService {
	protected Log logger = LogFactory.getLog(this.getClass());

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
				// Exception in thread "Thread-11" java.lang.ClassCastException: [B cannot be cast to java.util.List
				try {
					subscribe();
				}
				catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			};
		}.start();
	}

	protected void subscribe() throws InterruptedException {
		while (true) {
			try {
				redis.subscribe(queueListener, channel);
			}
			catch (ClassCastException e) {
				// FIXME 为什么会报错?
				// logger.error(e.getMessage(), e);
				Thread.sleep(1000);
			}
		}
	}

	@Override
	public void destroy() {

	}

}
