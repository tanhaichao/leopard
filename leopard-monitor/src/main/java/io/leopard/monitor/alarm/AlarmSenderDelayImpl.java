package io.leopard.monitor.alarm;

import io.leopard.data.AlarmSender;
import io.leopard.data.AlarmSenderImpl;

public class AlarmSenderDelayImpl implements AlarmSender {

	private static AlarmService alarmService = AlarmModule.getAlarmService();

	public static void init() {
		AlarmSenderImpl alarmSender = (AlarmSenderImpl) AlarmSenderImpl.getInstance();
		alarmSender.setAlarmSender(new AlarmSenderDelayImpl());
		// }
	}

	@Override
	public boolean send(String message, Throwable t) {
		return alarmService.send(message, t);
	}

	@Override
	public boolean send(String message) {
		return alarmService.send(message);
	}

}
