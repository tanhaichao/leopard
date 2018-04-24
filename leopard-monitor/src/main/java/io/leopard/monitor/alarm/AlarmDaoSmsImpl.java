package io.leopard.monitor.alarm;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.lang.ContextImpl;
import io.leopard.burrow.util.ListUtil;
import io.leopard.commons.utility.ServerUtil;
import io.leopard.data.env.EnvUtil;
import io.leopard.data4j.log.Log4jFactory;
import io.leopard.httpnb.Httpnb;
import io.leopard.monitor.MonitorServiceImpl;
import io.leopard.monitor.model.Notifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public class AlarmDaoSmsImpl extends ContextImpl implements AlarmDao {
	protected Log alarmLogger = Log4jFactory.getAlarmLogger(AlarmDaoSmsImpl.class);

	private final AlarmFrequencyDao alarmFrequencyDaoMemoryImpl = new AlarmFrequencyDaoMemoryImpl();

	public static final String LEOPARD_VERSION = "1.1.1";

	private final int intervalSeconds = 60 * 10;// 10分钟发一次
	static {
		System.setProperty("robot.leopard.io", "robot.leopard.io");
	}
	private static boolean smsEnable = false;

	public static void setSmsEnable(boolean smsEnable) {
		// System.err.println("setSmsEnable:" + smsEnable);
		AlarmDaoSmsImpl.smsEnable = smsEnable;
	}

	@Override
	public boolean send(String message, Throwable t) {
		String content;
		if (StringUtils.isEmpty(message)) {
			content = StringUtils.defaultString(t.getMessage());
		}
		else {
			content = message;
		}
		// = AlarmUtil.getStackTrace(message, t);
		content = AlarmUtil.removeUseless(content);// 过滤掉中括号中的内容

		boolean isNeedSend = this.isNeedSend(content);
		if (!isNeedSend) {
			return false;
		}
		alarmFrequencyDaoMemoryImpl.add(content);
		// System.err.println("sms message:" + message + " smsEnable:" +
		// smsEnable);
		if (smsEnable) {
			try {
				this.sendSms(message);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		alarmLogger.debug(content);
		return true;
	}

	protected void sendSms(String message) {
		List<Notifier> notifierList = MonitorServiceImpl.getMonitorConfig().getNotifierList();
		notifierList = ListUtil.defaultList(notifierList);

		for (Notifier notifier : notifierList) {
			String mobile = notifier.getMobile();

			this.sendSms(mobile, message);
		}
	}

	protected void sendSms(String mobile, String message) {
		String code = EnvUtil.getProjectCode();
		String ip = ServerUtil.getServerIp();

		AssertUtil.assertNotEmpty(mobile, "手机号码不能为空.");
		AssertUtil.assertNotEmpty(message, "message不能为空.");

		alarmLogger.debug("sms mobile:" + mobile + " message:" + message);
		// logger.debug("sms mobile:" + mobile + " message:" + message);
		// System.err.println("sms code:" + code + " mobile:" + mobile +
		// " message:" + message);

		String url = "http://robot.leopard.io/webservice/alarm.do?code=" + code + "&ip=" + ip + "&mobile=" + mobile + "&version=" + LEOPARD_VERSION;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("message", message);
		Httpnb.doPost(url, 2000, params);
	}

	@Override
	public boolean isNeedSend(String message) {
		return alarmFrequencyDaoMemoryImpl.isNeedSend(message, intervalSeconds);
	}

}
