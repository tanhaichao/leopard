package io.leopard.monitor.alarm;

public interface AlarmDao {

	boolean send(String message, Throwable t);

	boolean isNeedSend(String message);

}
