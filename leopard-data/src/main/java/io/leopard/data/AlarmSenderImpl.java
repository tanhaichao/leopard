package io.leopard.data;

public class AlarmSenderImpl implements AlarmSender {

	private static AlarmSender instance = new AlarmSenderImpl();

	public static AlarmSender getInstance() {
		return instance;
	}

	private AlarmSender alarmSender;

	public void setAlarmSender(AlarmSender alarmSender) {
		this.alarmSender = alarmSender;
	}

	@Override
	public boolean send(String message, Throwable t) {
		if (alarmSender == null) {
			return false;
		}
		return alarmSender.send(message, t);
	}

	@Override
	public boolean send(String message) {
		return this.send(message, null);
	}

}
