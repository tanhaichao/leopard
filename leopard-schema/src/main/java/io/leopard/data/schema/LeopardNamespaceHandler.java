package io.leopard.data.schema;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class LeopardNamespaceHandler extends NamespaceHandlerSupport {

	public boolean isUseH2() {
		String useH2 = System.getProperty("useH2");
		return "true".equals(useH2);
	}

	@Override
	public void init() {
		// System.err.println("LeopardNamespaceHandler init useH2:" + System.getProperty("useH2"));

		registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());

		registerParser("redis", "io.leopard.schema.RedisBeanDefinitionParser");
		registerParser("memdb", "io.leopard.schema.MemdbBeanDefinitionParser");
		registerParser("pub", "io.leopard.schema.PubBeanDefinitionParser");
		registerParser("jdbc", "io.leopard.schema.JdbcBeanDefinitionParser");
		registerParser("memcache", "io.leopard.schema.MemcacheBeanDefinitionParser");
		registerParser("mysql-dsn", "io.leopard.schema.MysqlDsnBeanDefinitionParser");
		registerParser("redis-dsn", "io.leopard.schema.RedisDsnBeanDefinitionParser");
		registerParser("mongo-dsn", "io.leopard.schema.MongoDsnBeanDefinitionParser");
		registerParser("dfs-dsn", "io.leopard.schema.DfsDsnBeanDefinitionParser");
		registerParser("queue-dsn", "io.leopard.schema.QueueDsnBeanDefinitionParser");
		registerParser("memcache-dsn", "io.leopard.schema.MemcacheDsnBeanDefinitionParser");
		registerParser("counter", "io.leopard.schema.CounterBeanDefinitionParser");
		registerParser("captcha", "io.leopard.schema.CaptchaBeanDefinitionParser");
		registerParser("signature", "io.leopard.schema.SignatureBeanDefinitionParser");
		registerParser("tx", "io.leopard.schema.TxBeanDefinitionParser");

		// registerParser("timer-scan", "io.leopard.schema.TimerScanBeanDefinitionParser");
		// registerParser("connection-limit", "io.leopard.schema.ConnectionLimitBeanDefinitionParser");
		// registerParser("config", "io.leopard.schema.ConfigBeanDefinitionParser");
		// registerParser("performance", "io.leopard.schema.PerformanceBeanDefinitionParser");
		// registerParser("server", "io.leopard.schema.ServerBeanDefinitionParser");
	}

	protected void registerParser(String elementName, String className) {
		BeanDefinitionParser parser;
		try {
			parser = getBeanDefinitionParser(elementName, className);
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		// try {
		// parser = (BeanDefinitionParser) Class.forName(className).newInstance();
		// }
		// catch (Exception e) {
		// // throw new RuntimeException(e.getMessage(), e);
		// return;
		// }

		registerBeanDefinitionParser(elementName, parser);
	}

	protected BeanDefinitionParser getBeanDefinitionParser(String elementName, String className) throws Exception {
		boolean useH2 = isUseH2();
		if (!useH2) {
			return (BeanDefinitionParser) Class.forName(className).newInstance();
		}

		String h2ClassName = className.replace("io.leopard.schema", "io.leopard.schema.h2");
		Class<?> clazz;
		try {
			clazz = Class.forName(h2ClassName);
		}
		catch (ClassNotFoundException e) {
			clazz = Class.forName(className);
		}
		return (BeanDefinitionParser) clazz.newInstance();
	}
}