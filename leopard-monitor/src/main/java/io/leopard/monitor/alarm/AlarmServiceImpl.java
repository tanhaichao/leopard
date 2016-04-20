package io.leopard.monitor.alarm;

import com.google.inject.Inject;

public class AlarmServiceImpl implements AlarmService {

	@Inject
	private AlarmDao alarmDao;

	// private static AlarmService alarmService;
	//
	// public static AlarmService getInstance() {
	// if (alarmService == null) {
	// alarmService = new AlarmServiceImpl();
	// }
	// return alarmService;
	// }

	@Override
	public boolean send(String message) {
		return this.send(message, null);
	}

	@Override
	public boolean send(String message, Throwable t) {
		return this.alarmDao.send(message, t);
	}

}
