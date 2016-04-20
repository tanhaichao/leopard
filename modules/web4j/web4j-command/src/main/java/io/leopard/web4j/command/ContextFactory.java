package io.leopard.web4j.command;

import org.apache.commons.lang.StringUtils;

/**
 * @author 阿海
 * 
 */
public class ContextFactory {

	protected static IContextFactory contextFactory = newInstance();

	public static final String FACTORY_PROPERTYNAME = "leopard.ContextFactory";

	protected static IContextFactory newInstance() {
		String className = System.getProperty(FACTORY_PROPERTYNAME);
		if (StringUtils.isEmpty(className)) {
			return new ContextFactoryImpl();
		}
		try {
			Class<?> clazz = Class.forName(className);
			return (IContextFactory) clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 获取bean对象.
	 * 
	 * @param c
	 *            类型
	 * @return
	 */
	public static <E> E getBean(Class<E> c) {
		return contextFactory.getBean(c);
	}

	/**
	 * 获取bean对象.
	 * 
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	public static Object getBean(String beanName) {
		return contextFactory.getBean(beanName);
	}

	/**
	 * 关闭spring IOC容器.
	 */
	public static void shutDown() {
		contextFactory.shutDown();
	}

	/**
	 * 退出.
	 */
	public static void exit() {
		contextFactory.exit();
	}

}
