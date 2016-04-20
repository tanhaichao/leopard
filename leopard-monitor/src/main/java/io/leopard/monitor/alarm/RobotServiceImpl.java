package io.leopard.monitor.alarm;

import io.leopard.commons.utility.ServerUtil;
import io.leopard.data4j.env.EnvUtil;

import org.springframework.util.StringUtils;

import com.google.inject.Inject;

public class RobotServiceImpl implements RobotService {

	@Inject
	private RobotDao robotDao;
	@Inject
	private AlarmService alarmService;

	@Override
	public boolean errorlog(String level, String message, Throwable t) {
		alarmService.send(message, t);

		if (StringUtils.isEmpty(message)) {
			message = t.getMessage();
		}
		String code = EnvUtil.getProjectCode();
		String ip = ServerUtil.getServerIp();

		if ("robot".equals(code)) {
			return false;
		}

		return robotDao.errorlog(code, level, ip, message);
	}
}
