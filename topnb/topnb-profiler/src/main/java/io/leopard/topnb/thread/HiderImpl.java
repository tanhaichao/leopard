package io.leopard.topnb.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class HiderImpl implements Hider {

	private List<Hider> list = new ArrayList<Hider>();

	public HiderImpl() {
		ServiceLoader<Hider> loader = ServiceLoader.load(Hider.class);
		Iterator<Hider> iterator = loader.iterator();
		while (iterator.hasNext()) {
			Hider hider = iterator.next();
			list.add(hider);
		}
		list.add(new HiderPrefixImpl());
	}

	@Override
	public Boolean isHide(String threadName, StackTraceElement element) {
		for (Hider hider : list) {
			Boolean isHide = hider.isHide(threadName, element);
			if (isHide != null) {
				return isHide;
			}
		}
		return false;
	}

	public static class HiderPrefixImpl implements Hider {

		private List<String> list = new ArrayList<String>();

		public HiderPrefixImpl() {

			list.add("java.util.TimerThread");
			// web server
			list.add("org.apache.commons.httpclient.MultiThreadedHttpConnectionManager");
			list.add("com.mchange.v2.async.ThreadPoolAsynchronousRunner");

			list.add("java.lang.");
			list.add("sun.nio.ch.");
			list.add("sun.misc.");
			list.add("org.eclipse.jetty.");
			list.add("sun.reflect.");
			list.add("org.springframework.");
			list.add("javax.servlet.http.");
			list.add("java.util.concurrent.locks.");
			list.add("java.util.TimerThread.");
			// resin
			list.add("com.caucho.");
			list.add("jrockit.net.");
		}

		@Override
		public Boolean isHide(String threadName, StackTraceElement element) {
			for (String prefix : list) {
				if (threadName.startsWith(prefix)) {
					return true;
				}
			}
			return null;
		}

	}

}
