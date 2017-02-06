package io.leopard.mvc.trynb;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorUtil {
	protected static final Log logger = LogFactory.getLog(ErrorUtil.class);

	private static Map<String, String> MESSAGE_MAP = new HashMap<String, String>();
	static {
		MESSAGE_MAP.put("org.springframework.dao.DataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.CannotGetJdbcConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.RecoverableDataAccessException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.DuplicateKeyException", "数据库主键重复，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.DataIntegrityViolationException", "字段太长，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.dao.TransientDataAccessResourceException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("org.springframework.jdbc.BadSqlGrammarException", "非法SQL语句，请稍后重试.");
		MESSAGE_MAP.put("redis.clients.jedis.exceptions.JedisConnectionException", "操作数据库出错，请稍后重试.");
		MESSAGE_MAP.put("io.leopard.core.exception.other.OutSideException", "访问外部接口出错，请稍后重试.");
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
			return "更新程序后，还没有重启服务.";
		}
		if (e instanceof SQLException) {
			return "操作数据库出错，请稍后重试.";
		}
		message = e.getMessage();
		if (message == null) {
			return null;
		}
		return fillterDebugInfo(message);
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
