package io.leopard.mvc.trynb;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import com.mysql.jdbc.MysqlDataTruncation;

public class ErrorUtil {
	protected static final Log logger = LogFactory.getLog(ErrorUtil.class);

	private static Map<String, String> MESSAGE_MAP = new HashMap<String, String>();
	static {
		MESSAGE_MAP.put("org.springframework.dao.DataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.CannotGetJdbcConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.RecoverableDataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.DuplicateKeyException", "数据库主键重复，请稍后重试.");
		// MESSAGE_MAP.put("org.springframework.dao.DataIntegrityViolationException", "字段太长，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.TransientDataAccessResourceException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.BadSqlGrammarException", "非法SQL语句，请稍后重试.");
		MESSAGE_MAP.put("redis.clients.jedis.exceptions.JedisConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("io.leopard.core.exception.other.OutSideException", "访问外部接口出错，请稍后重试.");
	}

	/**
	 * 系统初始化时间
	 */
	private static Date startupTime = new Date();

	private static void printStartupTime() {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startupTime);
		logger.info("系统启动时间:" + time);
	}

	/**
	 * 获取异常信息.
	 * 
	 * @param e
	 * @return
	 */
	public static String parseMessage(Throwable e) {
		if (e == null) {
			throw new IllegalArgumentException("exception不能为空?");
		}

		String className = e.getClass().getName();

		String message = MESSAGE_MAP.get(className);
		if (message != null) {
			return message;
		}
		if (e instanceof GenericSignatureFormatError) {
			printStartupTime();
			return "更新程序后，还没有重启服务.";
		}
		if (e instanceof NoSuchMethodError) {
			printStartupTime();
			return "NoSuchMethodError:方法找不到.";
		}
		if (e instanceof SQLException) {
			return "操作数据库出错，请稍后重试.";
		}
		if (e instanceof DataIntegrityViolationException) {
			try {
				return parseDataIntegrityViolationException((DataIntegrityViolationException) e);
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return "字段太长，请稍后重试.";
			}

		}
		message = e.getMessage();
		if (message == null) {
			return null;
		}
		return fillterDebugInfo(message);
	}

	protected static String parseDataIntegrityViolationException(DataIntegrityViolationException e) {
		MysqlDataTruncation e1 = (MysqlDataTruncation) e.getCause();
		String message = e1.getMessage();
		// String message = "Data truncation: Data too long for column 'spec' at row 1";
		// [Data truncation: Out of range value for column 'weight' at row 1]

		// String regex = "Data truncation: Data too long for column '(.*?)' at row";
		String regex = " for column '(.*?)' at row";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find()) {
			String columnName = m.group(1);

			if (message.startsWith("Data truncation: Data too long for column")) {
				return "字段" + columnName + "太长，请稍后重试.";
			}
			else if (message.startsWith("Out of range value for column")) {
				return "数字类型字段" + columnName + "越界.";
			}
		}
		throw new RuntimeException("解析mysql提示信息出错[" + message + "].");
	}

	/**
	 * 过滤debug信息.
	 * 
	 * @param message
	 * @return
	 */
	protected static String fillterDebugInfo(String message) {
		message = message.replaceAll("\\[[^\\[]*?\\]", "");
		return message.replaceAll("\\[.*?\\]", "");
		// return message;
	}
}
