package io.leopard.monitor.alarm;


public class RobotDaoHttpImpl implements RobotDao {

	static {
		System.setProperty("robot.leopard.io", "robot.leopard.io");
	}

	@Override
	public boolean errorlog(String code, String level, String ip, String message) {
		// String url = "http://robot.leopard.io/webservice/errorlog/log.do?code=" + code + "&level=" + level + "&ip=" + ip + "&version=" + AlarmDaoSmsImpl.LEOPARD_VERSION;
		//
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("message", message);
		// try {
		// HttpUtils.doPost(url, params, 2000, 2000);
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		return true;
	}
}
