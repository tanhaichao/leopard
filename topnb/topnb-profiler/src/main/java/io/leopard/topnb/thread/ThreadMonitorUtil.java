package io.leopard.topnb.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadMonitorUtil {
	// private static Map<String, String> CATEGORY_EQUALS_MAP = new ConcurrentHashMap<String, String>();
	private static Map<String, String> CATEGORY_PREFIXE_MAP = new ConcurrentHashMap<String, String>();
	private static Map<String, String> CATEGORY_INDEXOF_MAP = new ConcurrentHashMap<String, String>();

	private static List<String> COMPANY_PREFIX_LIST = new ArrayList<String>();

	private static List<String> IGNORE_PREFIX_LIST = new ArrayList<String>();
	private static Set<String> IGNORE_THREAD_NAME_SET = new HashSet<String>();
	static {
		CATEGORY_PREFIXE_MAP.put("com.duowan.leopard.util.timer.", ThreadCategory.TIMER.getDesc());
		CATEGORY_PREFIXE_MAP.put("com.duowan.leopard.commons.utility.SystemUtil.sleep", ThreadCategory.TIMER.getDesc());
		CATEGORY_PREFIXE_MAP.put("org.apache.commons.pool.impl.GenericObjectPool", ThreadCategory.CONNECTION.getDesc());
		CATEGORY_PREFIXE_MAP.put("com.duowan.leopard.data.redis.JedisPool", ThreadCategory.CONNECTION.getDesc());
		CATEGORY_PREFIXE_MAP.put("redis.clients.util.Pool.getResource", ThreadCategory.CONNECTION.getDesc());

		// /

		CATEGORY_INDEXOF_MAP.put(".dao.", ThreadCategory.DAO.getDesc());
		CATEGORY_INDEXOF_MAP.put(".service.", ThreadCategory.SERVICE.getDesc());

	}

	static {
		COMPANY_PREFIX_LIST.add("com.duowan");
		COMPANY_PREFIX_LIST.add("cn.kuaikuai");
		COMPANY_PREFIX_LIST.add("com.yy");
	}

	static {
		IGNORE_THREAD_NAME_SET.add("java.util.TimerThread.mainLoop(Timer.java:509)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.leopard.data.redis.JedisPool.getResource(JedisPool.java:1)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.leopard.data.redis.JedisPool.getResource(JedisPool.java:43)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.leopard.monitor.performance.PerformanceMonitorInterceptor.invoke(PerformanceMonitorInterceptor.java:56)");
		// web server
		IGNORE_THREAD_NAME_SET.add("org.apache.commons.httpclient.MultiThreadedHttpConnectionManager$ReferenceQueueThread.run(MultiThreadedHttpConnectionManager.java:1122)");
		IGNORE_THREAD_NAME_SET.add("com.mchange.v2.async.ThreadPoolAsynchronousRunner$PoolThread.run(ThreadPoolAsynchronousRunner.java:534)");

		// udb
		IGNORE_THREAD_NAME_SET.add("com.duowan.universal.login.client.YYSecCenterOpenWSInvoker.changeAccesstoken(YYSecCenterOpenWSInvoker.java:143)	");
		IGNORE_THREAD_NAME_SET.add("com.duowan.udb.auth.UserinfoForOauth.validateOAuthCookie(UserinfoForOauth.java:319)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.udb.auth.UserinfoForOauth.validateOAuthCookie4Accesstoken(UserinfoForOauth.java:350)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.universal.login.client.ClientHelper.request(ClientHelper.java:184)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.udb.auth.UserinfoForOauth.dovalidateOAuthCookie(UserinfoForOauth.java:333)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.universal.login.client.ClientHelper.request(ClientHelper.java:149)");
		IGNORE_THREAD_NAME_SET.add("com.duowan.universal.login.client.ClientHelper.post(ClientHelper.java:123)");

		// //////////
		IGNORE_PREFIX_LIST.add("java.lang.");
		IGNORE_PREFIX_LIST.add("sun.nio.ch.");
		IGNORE_PREFIX_LIST.add("sun.misc.");
		IGNORE_PREFIX_LIST.add("org.eclipse.jetty.");
		IGNORE_PREFIX_LIST.add("sun.reflect.");
		IGNORE_PREFIX_LIST.add("org.springframework.");
		IGNORE_PREFIX_LIST.add("javax.servlet.http.");
		IGNORE_PREFIX_LIST.add("java.util.concurrent.locks.");
		IGNORE_PREFIX_LIST.add("java.util.TimerThread.");
		// resin
		IGNORE_PREFIX_LIST.add("com.caucho.");
		IGNORE_PREFIX_LIST.add("jrockit.net.");
		// //
		IGNORE_PREFIX_LIST.add("com.duowan.leopard.web.mvc.controller.PerformanceMonitorController.");
		IGNORE_PREFIX_LIST.add("com.duowan.leopard.monitor.thread.");
		IGNORE_PREFIX_LIST.add("com.duowan.leopard.web.userinfo.UserinfoFilter.");

	}

	public static String getCategory(String threadName) {
		// {
		// String category = CATEGORY_EQUALS_MAP.get(threadName);
		// if (category != null) {
		// // System.out.println("threadName:" + threadName + " category:" + category);
		// return category;
		// }
		// }
		{
			for (String prefix : CATEGORY_PREFIXE_MAP.keySet()) {
				if (threadName.startsWith(prefix)) {
					return CATEGORY_PREFIXE_MAP.get(prefix);
				}
			}
		}
		{
			for (String substr : CATEGORY_INDEXOF_MAP.keySet()) {
				if (threadName.indexOf(substr) != -1) {
					return CATEGORY_INDEXOF_MAP.get(substr);
				}
			}
		}
		// System.out.println("threadName:" + threadName + " UNKNOWN:" + ThreadCategory.UNKNOWN.getDesc());
		return ThreadCategory.UNKNOWN.getDesc();
	}

	protected static boolean isCompanyThreadName(String threadName) {
		for (String company : COMPANY_PREFIX_LIST) {
			if (threadName.startsWith(company)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isIgnoreThreadName(String threadName) {
		if (!isCompanyThreadName(threadName)) {
			return true;
		}
		if (threadName.indexOf("$$") != -1) {
			// 忽略AOP类 .
			return true;
		}
		for (String prefix : IGNORE_PREFIX_LIST) {
			if (threadName.startsWith(prefix)) {
				return true;
			}
		}
		return IGNORE_THREAD_NAME_SET.contains(threadName);
	}
}
