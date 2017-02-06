package io.leopard.test;

import org.springframework.core.io.ClassPathResource;

public class ApplicationContextLocationFirstImpl implements ApplicationContextLocation {

	/** 第一个入口 */
	private static final String ENTRY_FIRST = "/integrationTest.xml";

	@Override
	public String[] get() {
		ClassPathResource resource = new ClassPathResource(ENTRY_FIRST);
		// System.err.println("resource.exists():" + resource.exists() + " ENTRY_FIRST:" + ENTRY_FIRST);
		if (resource.exists()) {
			return new String[] { ENTRY_FIRST };
		}
		return null;
	}
}
