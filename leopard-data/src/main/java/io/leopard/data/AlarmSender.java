package io.leopard.data;

/**
 * 报警信息发送器.
 * 
 * @author 阿海
 * 
 */
public interface AlarmSender {
	boolean send(String message);

	boolean send(String message, Throwable t);
}
