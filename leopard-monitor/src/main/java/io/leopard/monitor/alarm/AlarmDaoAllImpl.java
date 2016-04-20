package io.leopard.monitor.alarm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public class AlarmDaoAllImpl implements AlarmDao {

	private AlarmDao[] alarmDaos;

	public AlarmDaoAllImpl() {
		List<AlarmDao> alarmDaoList = new ArrayList<AlarmDao>();
		//
		alarmDaoList.add(new AlarmDaoSmsImpl());
		//
		this.alarmDaos = new AlarmDao[alarmDaoList.size()];
		alarmDaoList.toArray(this.alarmDaos);
	}

	@Override
	public boolean send(String message, Throwable t) {
		for (AlarmDao alarmDao : this.alarmDaos) {
			alarmDao.send(message, t);
		}
		// System.err.println("message:" + message);
		return true;
	}

	@Override
	public boolean isNeedSend(String message) {
		throw new NotImplementedException();
	}

}
