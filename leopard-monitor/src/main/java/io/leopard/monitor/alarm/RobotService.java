package io.leopard.monitor.alarm;

/**
 * 机器人接口.
 * 
 * @author 阿海
 * 
 */
public interface RobotService {

	boolean errorlog(String level, String message, Throwable t);

}
