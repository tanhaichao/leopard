package io.leopard.data.queue;

import io.leopard.commons.utility.SystemUtil;

import org.junit.Test;

public class QueueRedisImplIntegrationTest implements IConsumer {

	@Test
	public void basicPublish() {
		QueueRedisImpl channel = new QueueRedisImpl();
		channel.setServer("127.0.0.1:6311");
		channel.init();
		channel.publish("queue-test", "message-1");
		channel.publish("queue-test", "message-2");
		channel.publish("queue-test", "message-3");

		channel.consume("queue-test", this);

		SystemUtil.sleep(1000 * 10);
	}

	@Override
	public void consume(String message) throws Exception {
		System.err.println("message:" + message);
	}

}