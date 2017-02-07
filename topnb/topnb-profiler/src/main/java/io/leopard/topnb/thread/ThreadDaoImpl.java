package io.leopard.topnb.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreadDaoImpl implements ThreadDao {

	private Hider hider = new HiderImpl();

	@Override
	public List<ThreadInfo> listAll() {
		Thread[] threads = this.listAllThreads();
		Map<String, ThreadInfo> map = new HashMap<String, ThreadInfo>();
		for (Thread thread : threads) {
			StackTraceElement[] stacks = thread.getStackTrace();
			for (StackTraceElement stack : stacks) {
				String threadName = stack.toString();
				if (hider.isHide(threadName, stack)) {
					continue;
				}
				ThreadInfo threadInfo = map.get(threadName);
				if (threadInfo == null) {
					threadInfo = new ThreadInfo();
					threadInfo.setElement(stack);
					threadInfo.setThreadName(threadName);
					map.put(threadName, threadInfo);
				}
				threadInfo.setCurrentSize(threadInfo.getCurrentSize() + 1);
			}
		}

		Iterator<Entry<String, ThreadInfo>> iterator = map.entrySet().iterator();
		List<ThreadInfo> list = new ArrayList<ThreadInfo>();
		while (iterator.hasNext()) {
			Entry<String, ThreadInfo> entry = iterator.next();
			list.add(entry.getValue());
		}
		return list;
	}

	@Override
	public ThreadGroup getThreadGroup() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup parent = null;
		while ((parent = group.getParent()) != null) {
			group = parent;
		}
		return group;
	}

	@Override
	public Thread[] listAllThreads() {
		ThreadGroup group = this.getThreadGroup();
		Thread[] threads = new Thread[group.activeCount()];
		group.enumerate(threads);
		return threads;
	}

}
