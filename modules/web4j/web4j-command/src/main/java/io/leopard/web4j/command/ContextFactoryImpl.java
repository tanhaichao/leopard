package io.leopard.web4j.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextFactoryImpl implements IContextFactory {

	protected static final Log logger = LogFactory.getLog(ContextFactoryImpl.class);

	private static AbstractApplicationContext applicationContext;

	protected synchronized AbstractApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			// String[] filenames = { "classpath:/env/config.xml", "/applicationContext-command.xml" };
			applicationContext = getApplicationContext("/applicationContext-command.xml");
		}
		return applicationContext;
	}

	public synchronized AbstractApplicationContext getApplicationContext(String configLocation) {
		String[] filenames = { "classpath:/leopard/applicationContext.xml", configLocation };
		return new ClassPathXmlApplicationContext(filenames);
	}

	/**
	 * 获取bean对象.
	 * 
	 * @param c
	 *            类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getBean(Class<E> c) {
		String name = c.getSimpleName();
		String beanName = name.substring(0, 1).toLowerCase() + name.substring(1);
		String className = c.getName();
		// System.out.println("name:" + name);
		logger.info("beanName:" + beanName + " className:" + className);
		return (E) getBean(beanName);
	}

	/**
	 * 获取bean对象.
	 * 
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	@Override
	public Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	/**
	 * 关闭spring IOC容器.
	 */
	@Override
	public void shutDown() {
		getApplicationContext().registerShutdownHook();
	}

	/**
	 * 退出.
	 */
	@Override
	public void exit() {
		shutDown();
		System.exit(0);
	}
}
