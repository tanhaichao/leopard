package io.leopard.burrow.util;

import io.leopard.burrow.lang.Invoker;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 多线程执行代码，并等待所有线程执行完成。
 * 
 * @author 阿海
 * 
 */
public class Caller {
	protected Log logger = LogFactory.getLog(this.getClass());

	private Vector<Invoker> list = new Vector<Invoker>();

	private long start;

	public Caller() {
		this.start = System.currentTimeMillis();
	}

	public int add(Invoker invoker) {
		list.add(invoker);
		return list.size();
	}

	/**
	 * 开始执行..
	 * 
	 * @return 返回耗时.
	 */
	public long execute() {
		if (list.size() <= 0) {
			return System.currentTimeMillis() - start;
		}
		final CountDownLatch countDown = new CountDownLatch(list.size());
		for (final Invoker invoker : list) {
			new Thread() {
				@Override
				public void run() {
					try {
						invoker.execute();
					}
					catch (Throwable e) {
						logger.error(e.getMessage(), e);
					}
					countDown.countDown();
				}
			}.start();
		}
		try {
			countDown.await();
		}
		catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		long time = System.currentTimeMillis() - start;
		return time;
	}
}
