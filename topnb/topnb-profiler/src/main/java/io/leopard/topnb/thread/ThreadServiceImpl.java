package io.leopard.topnb.thread;

import io.leopard.topnb.thread.Classifier.Category;

import java.util.Collections;
import java.util.List;

public class ThreadServiceImpl implements ThreadService {

	private ThreadDao threadDao = new ThreadDaoImpl();

	private Classifier classifier = new ClassifierImpl();

	@Override
	public List<ThreadInfo> listAll() {
		List<ThreadInfo> list = threadDao.listAll();
		for (ThreadInfo threadInfo : list) {
			String threadName = threadInfo.getThreadName();
			StackTraceElement element = threadInfo.getElement();

			Category category = classifier.getCategory(threadName, element);
			threadInfo.setCategory(category);
		}
		Collections.sort(list, new CategoryThreadComparator());
		return list;
	}

	@Override
	public int getActiveCount() {
		ThreadGroup group = threadDao.getThreadGroup();
		return group.activeCount();
	}

}
