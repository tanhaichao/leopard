package io.leopard.monitor;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.schema.AbstractMonitorTests;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisMonitorIntegrationTest extends AbstractMonitorTests {

	@Autowired
	private RedisMonitor redisMonitor;

	@Test
	public void monitor() {
		redisMonitor.monitor();
	}

	@Test
	public void monitor2() {
		for (int i = 0; i < 10; i++) {

			redisMonitor.monitor();

			System.out.println("##################################");
			SystemUtil.sleep(1000);
		}
	}
}