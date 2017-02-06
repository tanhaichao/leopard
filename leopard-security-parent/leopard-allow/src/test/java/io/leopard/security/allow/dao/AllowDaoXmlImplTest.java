package io.leopard.security.allow.dao;

import org.junit.Test;

public class AllowDaoXmlImplTest {

	private AllowDaoXmlImpl allowDaoXmlImpl = new AllowDaoXmlImpl() {
		@Override
		protected java.io.InputStream read() throws java.io.IOException {
			return AllowDaoXmlImplTest.class.getResourceAsStream("allow.xml");
		};
	};

	@Test
	public void load() {
		Allow allow = new Allow();
		allow.setUri("/");
		allow.setIp("127.0.0.2");
		boolean flag = allowDaoXmlImpl.exist(allow);
		System.out.println("flag:" + flag);
	}

}