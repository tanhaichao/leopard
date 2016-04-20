package io.leopard.monitor.alarm;

/**
 * 频率限制.
 * 
 * @author ahai
 * 
 */
public interface AlarmFrequencyDao {

	boolean add(String message);

	boolean isNeedSend(String message, int intervalSeconds);

}
