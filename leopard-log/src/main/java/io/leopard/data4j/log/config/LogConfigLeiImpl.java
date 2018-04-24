package io.leopard.data4j.log.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LogConfigLeiImpl implements LogConfigLei {

	private static String content = null;

	public static String getContent() {
		return content;
	}

	private static String getEnv() {
		String env = System.getProperty("LENV");
		if (StringUtils.isEmpty(env)) {
			return "dev";
		}
		return env;
	}

	@Override
	public void configure() {
		try {
			Class.forName("org.apache.log4j.PropertyConfigurator");
		}
		catch (ClassNotFoundException e) {
			// 没用启用log4j配置.
			return;
		}
		String env = getEnv();
		Resource resource = getResource(env);
		if (resource != null) {
			try {
				System.out.println("log4j.properties -> " + resource.getURL().toString());
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			String content;
			try {
				InputStream is = resource.getInputStream();
				content = IOUtils.toString(is);
				is.close();
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			try {
				Class.forName("io.leopard.monitor.alarm.DailyAutoRollingFileAppender");
				Log4jPropertiesParser parser = new Log4jPropertiesParserImpl();
				content = parser.parse(content);
				// System.out.println("ytest clazz:" + content);
			}
			catch (ClassNotFoundException e) {

			}
			configure(content);
		}
	}

	@Override
	public Resource getResource(String env) {
		// System.err.println("env:" + env);
		Resource resource = new ClassPathResource("/log4j-" + env + ".properties");
		if (!resource.exists()) {
			resource = new ClassPathResource("/log4j.properties");
		}
		if (resource.exists()) {
			return resource;
		}
		else {
			return null;
		}
	}

	public static boolean configure(String content) {
		// System.err.println("log4j content:" + content);
		ByteArrayResource resource2 = new ByteArrayResource(content.getBytes(), "log4j.properties");
		try {
			InputStream input = resource2.getInputStream();
			configure(input);
			input.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		LogConfigLeiImpl.content = content;
		return true;
	}

	protected static void configure(InputStream input) {
		try {
			PropertyConfigurator.configure(input);
		}
		catch (NoSuchMethodError e) {

		}
	}
}
