package io.leopard.topnb.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

public class ClassifierImpl implements Classifier {

	// CONNECTION(90, "网络连接"), DAO(80, "Dao"), SERVICE(70, "Service"), TIMER(60, "定时器"), UNKNOWN(50, "未知");

	private List<Classifier> list = new ArrayList<Classifier>();

	public ClassifierImpl() {
		ServiceLoader<Classifier> loader = ServiceLoader.load(Classifier.class);
		Iterator<Classifier> iterator = loader.iterator();
		while (iterator.hasNext()) {
			Classifier classifier = iterator.next();
			list.add(classifier);
		}
		list.add(new ClassifierConnectionImpl());
		list.add(new ClassifierDaoImpl());
		list.add(new ClassifierServiceImpl());
		list.add(new ClassifierControllerImpl());
		list.add(new ClassifierTopnbImpl());
	}

	@Override
	public Category getCategory(String threadName, StackTraceElement element) {
		for (Classifier classifier : list) {
			Category category = classifier.getCategory(threadName, element);
			if (category != null) {
				return category;
			}
		}
		return new UnknownCategory();
	}

	public static class ClassifierServiceImpl implements Classifier {

		@Override
		public Category getCategory(String threadName, StackTraceElement element) {
			String className = element.getClassName();
			if (className.indexOf(".service") != -1) {
				return new ServiceCategory();
			}
			return null;
		}
	}

	public static class ClassifierDaoImpl implements Classifier {

		@Override
		public Category getCategory(String threadName, StackTraceElement element) {
			String className = element.getClassName();
			if (className.indexOf(".dao") != -1) {
				return new ServiceCategory();
			}
			return null;
		}
	}

	public static class ClassifierControllerImpl implements Classifier {

		@Override
		public Category getCategory(String threadName, StackTraceElement element) {
			String className = element.getClassName();
			if (className.indexOf(".controller") != -1) {
				return new ServiceCategory();
			}
			return null;
		}
	}

	public static class ClassifierConnectionImpl implements Classifier {

		private static Set<String> classNameSet = new HashSet<String>();

		static {
			classNameSet.add("org.apache.commons.pool.impl.GenericObjectPool");
			classNameSet.add("redis.clients.util.Pool.getResource");
		}

		// CATEGORY_PREFIXE_MAP.put("org.apache.commons.pool.impl.GenericObjectPool", ThreadCategory.CONNECTION.getDesc());
		// CATEGORY_PREFIXE_MAP.put("com.duowan.leopard.data.redis.JedisPool", ThreadCategory.CONNECTION.getDesc());
		// CATEGORY_PREFIXE_MAP.put("redis.clients.util.Pool.getResource", ThreadCategory.CONNECTION.getDesc());

		@Override
		public Category getCategory(String threadName, StackTraceElement element) {
			String className = element.getClassName();
			if (classNameSet.contains(className)) {
				return new ConnectionCategory();
			}
			return null;
		}
	}

	public static class ClassifierTopnbImpl implements Classifier {

		private static List<String> list = new ArrayList<String>();

		static {
			list.add("io.leopard.topnb.");
		}

		@Override
		public Category getCategory(String threadName, StackTraceElement element) {
			for (String prefix : list) {
				if (threadName.startsWith(prefix)) {
					return new TopnbCategory();
				}
			}
			return null;
		}
	}

	/**
	 * Connection.
	 * 
	 * @author 阿海
	 *
	 */
	public static class ConnectionCategory implements Category {

		@Override
		public String getKey() {
			return "connection";
		}

		@Override
		public String getName() {
			return "Connection";
		}

		@Override
		public int getOrder() {
			return 70;
		}

	}

	/**
	 * Service.
	 * 
	 * @author 阿海
	 *
	 */
	public static class ServiceCategory implements Category {

		@Override
		public String getKey() {
			return "service";
		}

		@Override
		public String getName() {
			return "Service";
		}

		@Override
		public int getOrder() {
			return 90;
		}

	}

	/**
	 * TopNB.
	 * 
	 * @author 阿海
	 *
	 */
	public static class TopnbCategory implements Category {

		@Override
		public String getKey() {
			return "topnb";
		}

		@Override
		public String getName() {
			return "TopNB";
		}

		@Override
		public int getOrder() {
			return 100;
		}

	}

	/**
	 * Spring.
	 * 
	 * @author 阿海
	 *
	 */
	public static class SpringCategory implements Category {

		@Override
		public String getKey() {
			return "spring";
		}

		@Override
		public String getName() {
			return "Spring";
		}

		@Override
		public int getOrder() {
			return 110;
		}

	}

	/**
	 * Service.
	 * 
	 * @author 阿海
	 *
	 */
	public static class DaoCategory implements Category {

		@Override
		public String getKey() {
			return "dao";
		}

		@Override
		public String getName() {
			return "Dao";
		}

		@Override
		public int getOrder() {
			return 80;
		}

	}

	/**
	 * 定时器.
	 * 
	 * @author 阿海
	 *
	 */
	public static class TimerCategory implements Category {

		@Override
		public String getKey() {
			return "timer";
		}

		@Override
		public String getName() {
			return "定时器";
		}

		@Override
		public int getOrder() {
			return 60;
		}

	}

	/**
	 * 未知分类.
	 * 
	 * @author 阿海
	 *
	 */
	public static class UnknownCategory implements Category {

		@Override
		public String getKey() {
			return "unknown";
		}

		@Override
		public String getName() {
			return "未知";
		}

		@Override
		public int getOrder() {
			return 50;
		}

	}

}
