package io.leopard.data.signature;

import io.leopard.timer.MilliSecondPeriod;
import io.leopard.timer.SimpleTimer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 签名消息队列管理器.
 * 
 * @author 阿海
 * 
 */
public abstract class SignatureQueueManager {
	protected Log logger = LogFactory.getLog(this.getClass());

	// 考虑到使用过的key有部分丢失影响不大，为了性能队列只是存在内存中，程序重启有可能会导致部分消息丢失.
	private Queue<String> queue = new ConcurrentLinkedQueue<String>();

	public SignatureQueueManager() {
		new SimpleTimer(new MilliSecondPeriod(100)) {
			@Override
			public void start() {
				consume();
			}
		}.run();
	}

	public boolean add(String sha1) {
		return queue.add(sha1);
	}

	protected void consume() {
		int count = 10000;
		for (int i = 0; i < count; i++) {
			String sha1 = queue.poll();
			if (sha1 == null) {
				break;
			}
			// System.err.println("poll:" + sha1);
			this.consume(sha1);

			if (i == count - 1) {
				this.printLog();
			}
		}
	}

	protected void printLog() {
		int size = this.queue.size();
		logger.warn("queueSize:" + size);
	}

	/**
	 * 消费者.
	 * 
	 * @param sha1
	 */
	public abstract void consume(String sha1);

}
