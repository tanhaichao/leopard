package io.leopard.data4j.memdb.rsync;

import io.leopard.data4j.memdb.MemdbRsyncQueue;
import io.leopard.data4j.memdb.QueueBean;
import io.leopard.data4j.memdb.QueueListener;
import io.leopard.data4j.memdb.SerializeImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class QueueListenerTest {
	MemdbRsyncQueue memdbRsyncQueue = Mockito.mock(MemdbRsyncQueue.class);

	private final QueueListener listener = new QueueListener(memdbRsyncQueue, "sender");

	@Test
	public void onSubscribe() {
		listener.onSubscribe("channel", 1);
	}

	@Test
	public void onMessage() {
		QueueBean bean = new QueueBean();
		bean.setKey("key");

		this.listener.onMessage("channel", SerializeImpl.getInstance().serialize(bean));
		bean.setSender("sender");
		this.listener.onMessage("channel", SerializeImpl.getInstance().serialize(bean));
	}

	@Test
	public void onUnsubscribe() {
		listener.onUnsubscribe("channel", 1);
	}

	@Test
	public void onPSubscribe() {
		listener.onPSubscribe("pattern", 1);
	}

	@Test
	public void onPUnsubscribe() {
		listener.onPUnsubscribe("pattern", 1);
	}

	@Test
	public void onPMessage() {
		listener.onPMessage("pattern", "channel", "message");
	}

}