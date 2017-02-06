package io.leopard.data4j.memdb;

import redis.clients.jedis.JedisPubSub;

public class QueueListener extends JedisPubSub {

	// private Log logger = LogFactory.getLog(QueueListener.class);

	private final MemdbRsyncQueue memdbRsyncQueue;

	private final String sender;

	public QueueListener(MemdbRsyncQueue memdbRsyncQueue, String sender) {
		this.memdbRsyncQueue = memdbRsyncQueue;
		this.sender = sender;
	}

	// 取得订阅的消息后的处理
	@Override
	public void onMessage(String channel, String message) {
		QueueBean queueBean = SerializeImpl.getInstance().toObject(message, QueueBean.class);

		boolean isMySelf = this.sender.equals(queueBean.getSender());
		// if (!listenOneSelf) {
		// if (this.sender.equals(queueBean.getSender())) {
		// // 忽略自己发送的消息.
		// // logger.info("忽略自己发送的消息:" + channel + "=" + message);
		// return;
		// }
		// }
		this.memdbRsyncQueue.add(queueBean.getType(), queueBean.getKey(), queueBean.getValue(), isMySelf);
		// System.out.println(channel + "=" + message);
	}

	// 初始化订阅时候的处理
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		// System.out.println(channel + "=" + subscribedChannels);
	}

	// 取消订阅时候的处理
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// System.out.println(channel + "=" + subscribedChannels);
	}

	// 初始化按表达式的方式订阅时候的处理
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// System.out.println(pattern + "=" + subscribedChannels);
	}

	// 取消按表达式的方式订阅时候的处理
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// System.out.println(pattern + "=" + subscribedChannels);
	}

	// 取得按表达式的方式订阅的消息后的处理
	@Override
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(pattern + "=" + channel + "=" + message);
	}

}
