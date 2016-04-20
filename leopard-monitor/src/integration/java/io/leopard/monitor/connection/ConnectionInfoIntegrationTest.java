package io.leopard.monitor.connection;

import io.leopard.commons.utility.Clock;

import org.junit.Test;

public class ConnectionInfoIntegrationTest {

	ConnectionInfo connectionInfo = new ConnectionInfo();

	@Test
	public void incrBrokenCount() {
		Clock clock = Clock.start();
		final int count = 100000000;
		for (int i = 0; i < count; i++) {
			this.test();
		}
		clock.time("time");
	}

	private void test() {
		connectionInfo.incrBrokenCount(1);
		connectionInfo.incrConnectionCount(1);
		connectionInfo.incrCurrentSize(1);
		connectionInfo.incrTotalTime(1);
	}
}