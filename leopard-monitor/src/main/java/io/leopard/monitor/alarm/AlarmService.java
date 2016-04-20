package io.leopard.monitor.alarm;

/**
 * 报警接口.
 * 
 * @author ahai
 * 
 */
public interface AlarmService {

	/**
	 * 发送报警信息
	 * 
	 * @param message
	 * @return
	 */
	boolean send(String message, Throwable t);

	boolean send(String message);
}
