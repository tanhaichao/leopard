package io.leopard.monitor.alarm;

import org.junit.Test;

public class AlarmServiceImplIntegrationTest {

	private AlarmServiceImpl alarmService = new AlarmServiceImpl();

	@Test
	public void send() {
		// alarmService.send("message");
		this.alarmService.send("msg", new Exception("msg"));
	}

}