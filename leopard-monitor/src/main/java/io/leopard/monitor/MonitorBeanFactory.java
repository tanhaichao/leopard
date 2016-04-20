package io.leopard.monitor;

import io.leopard.monitor.connection.ConnectionMonitorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MonitorBeanFactory {
	protected static final Log logger = LogFactory.getLog(MonitorBeanFactory.class);

	private static AbstractApplicationContext applicationContext;

	protected static synchronized AbstractApplicationContext getApplicationContext() {

		if (applicationContext == null) {
			String[] filenames = { "/performance/applicationContext-service.xml" };
			applicationContext = new ClassPathXmlApplicationContext(filenames);
		}
		return applicationContext;
	}

	public static ConnectionMonitorService getConnectionMonitorService() {
		return (ConnectionMonitorService) getBean("connectionMonitorService");
	}

	/**
	 * 获取bean对象.
	 * 
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	// /**
	// * 关闭spring IOC容器.
	// */
	// public static void shutDown() {
	// getApplicationContext().registerShutdownHook();
	// }
}
