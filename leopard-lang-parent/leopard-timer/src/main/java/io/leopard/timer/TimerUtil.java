package io.leopard.timer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimerUtil {
	protected final static Log logger = LogFactory.getLog("TIMERLOG." + TimerUtil.class.getName());

	/**
	 * 启动定时器 .
	 * 
	 * @param timer
	 *            定时器
	 */
	public static List<Thread> start(final Timer timer) {
		if (!timer.isEnabled()) {
			logger.info("当前服务器不启用定时器[" + timer.getClass().getSimpleName() + "]。");
			return null;
		}

		int threadCount = timer.getThreadCount();
		if (threadCount < 1) {
			throw new IllegalArgumentException("定时器线程数不能小于1.");
		}
		List<Thread> threadList = new ArrayList<Thread>();
		for (int i = 0; i < threadCount; i++) {
			// 运行定时任务
			String threadName = timer.getClass().getSimpleName() + "-" + i;
			Thread thread = new Thread(threadName) {
				@Override
				public void run() {
					TimerUtil.run(timer);
				}
			};
			thread.start();
			threadList.add(thread);
		}
		return threadList;
	}

	public static void run(Timer timer) {
		while (true) {
			Period period = timer.getPeriod();
			if (period == null) {
				throw new NullPointerException("定时器间隔周期没有设置.");
			}
			boolean isContinue = false;
			try {
				isContinue = period.sleep();
				timer.start();
			}
			catch (Throwable e) {
				logger.error(e.getMessage(), e);
			}
			if (!isContinue) {
				break;
			}
		}
	}
}
