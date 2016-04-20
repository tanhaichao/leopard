package io.leopard.monitor.alarm;

import org.junit.Test;

public class AlarmDaoSmsImplIntegrationTest {

	private AlarmDaoSmsImpl alarmDaoSmsImpl = new AlarmDaoSmsImpl();

	@Test
	public void send() {
		alarmDaoSmsImpl.send("message", new Exception("msg"));
	}

}