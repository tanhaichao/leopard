package io.leopard.monitor.alarm;

/**
 * 机器人接口.
 * 
 * @author 阿海
 * 
 */
public interface RobotDao {

	boolean errorlog(String code, String level, String ip, String message);

}
