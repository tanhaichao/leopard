package io.leopard.monitor.alarm;

import io.leopard.burrow.lang.SynchronizedLRUMap;

import java.util.Map;
import java.util.Set;

public class AlarmFrequencyDaoMemoryImpl implements AlarmFrequencyDao {

	// 同样的消息N秒钟只能发送一条
	// 全部消息，2个小时内最多发送10条
	//

	protected Map<String, Long> lastSendTimeMap = new SynchronizedLRUMap<String, Long>(10, 100);

	protected Map<Long, String> upperLimitMap = new SynchronizedLRUMap<Long, String>(100, 100);

	private static final long UPPER_LIMIT_TIME = 2 * 60 * 60 * 1000L;// 2小时(毫秒数)
	private static final int UPPER_LIMIT_COUNT = 10;// 10条

	@Override
	public boolean add(String message) {
		long time = System.currentTimeMillis();
		lastSendTimeMap.put(message, time);
		upperLimitMap.put(time, message);
		return true;
	}

	@Override
	public synchronized boolean isNeedSend(String message, int intervalSeconds) {
		boolean isSent = this.isSent(message, intervalSeconds);
		if (isSent) {
			// 已经发送过了
			return false;
		}
		boolean isUpperLimit = this.isUpperLimit();
		if (isUpperLimit) {
			// 全部消息，2个小时内已经达到10条
			return false;
		}
		return true;
	}

	/**
	 * 所有消息是否达到上限限制.
	 * 
	 * @return
	 */
	protected boolean isUpperLimit() {
		Set<Long> keySet = upperLimitMap.keySet();
		long limitTime = System.currentTimeMillis() - UPPER_LIMIT_TIME;

		int count = 0;
		for (Long time : keySet) {
			if (time > limitTime) {
				count++;
			}
		}
		return count >= UPPER_LIMIT_COUNT;
	}

	/**
	 * 是否已发送过?
	 * 
	 * @param message
	 * @param intervalSeconds
	 * @return
	 */
	protected boolean isSent(String message, int intervalSeconds) {
		Long lastSend = lastSendTimeMap.get(message);
		if (lastSend == null) {
			return false;
		}
		long diff = (System.currentTimeMillis() - lastSend) / 1000;
		// System.out.println("diff:" + diff);
		return (diff < intervalSeconds);
	}

}
