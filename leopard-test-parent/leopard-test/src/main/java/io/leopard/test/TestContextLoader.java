package io.leopard.test;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.SmartContextLoader;
import org.springframework.util.StringUtils;

/**
 * 
 * @author 阿海
 * 
 */
public class TestContextLoader implements SmartContextLoader {

	// @Override
	// public String[] processLocations(Class<?> clazz, String... locations) {
	// return locations;
	// }
	@Override
	public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
		String className = "io.leopard.javahost.AutoUnitRunnable";
		try {
			Runnable runnable = (Runnable) Class.forName(className).newInstance();
			runnable.run();
		}
		catch (Exception e) {
			// System.err.println("init hosts error:" + e.toString());
			// e.printStackTrace();
		}
		String[] locations = new String[0];
		if (locations.length == 0) {
			locations = new ApplicationContextLocationImpl().get();
		}
		// files = ArrayUtil.insertFirst(files, "/leopard-test/annotation-config.xml");
		locations = StringUtils.addStringToArray(locations, "/leopard-test/annotation-config.xml");
		configAttributes.setLocations(locations);
		// System.err.println("processContextConfiguration:" + org.apache.commons.lang.StringUtils.join(configAttributes.getLocations(), ","));
	}

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {
		// System.err.println("loadContext:" + org.apache.commons.lang.StringUtils.join(locations, ","));
		return new ClassPathXmlApplicationContext(locations);
	}

	@Override
	public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
		// System.err.println("loadContext:" + mergedConfig);
		return loadContext(mergedConfig.getLocations());
	}

	@Override
	public String[] processLocations(Class<?> clazz, String... locations) {
		throw new NotImplementedException();
	}

}
