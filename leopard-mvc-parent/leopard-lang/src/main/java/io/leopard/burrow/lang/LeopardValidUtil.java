package io.leopard.burrow.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import io.leopard.burrow.util.DateTime;

/**
 * 参数合法性验证.
 * 
 * @author 阿海
 * 
 */
public class LeopardValidUtil {

	private static Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9][a-z0-9_\\-]+$");

	public static boolean isValidUsername(String username) {
		if (username == null || username.length() == 0) {
			return false;
		}
		// return UdbConstant.isValidUsername(username);
		Matcher m = USERNAME_PATTERN.matcher(username);
		return m.find();
	}

	/**
	 * 是否非法的用户名
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isNotValidUsername(String username) {
		return !isValidUsername(username);
	}

	public static boolean isValidPassport(String passport) {
		return LeopardValidUtil.isValidUsername(passport);
	}

	/**
	 * 验证uid是否合法 .
	 * 
	 * @param yyuid
	 * @return 合法 false 非法true
	 */
	public static boolean isValidUid(long uid) {
		if (uid <= 0) {
			return false;
		}
		return true;
	}

	private static Pattern IP_PATTERN = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");

	/**
	 * 是否合法IP？ .
	 * 
	 * TODO 方法实现需要优化
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isValidIp(String ip) {
		if (ip == null || ip.length() == 0) {
			return false;
		}
		if (ip.length() < 7) {
			return false;
		}
		Matcher m = IP_PATTERN.matcher(ip);
		return m.find();
	}

	/**
	 * 是否合法的时间格式？ .
	 * 
	 * @param datetime
	 * @return
	 */
	public static boolean isValidDateTime(String datetime) {
		return DateTime.isDateTime(datetime);
	}

	/**
	 * 判断对象是否为null..
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 判断对象是否不为null..
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	private static String ID_REGEX = "^[a-zA-Z0-9_\\-]{32}$";

	/**
	 * 允许非法的uuid
	 */
	private static boolean allowInvalidUuid = false;

	public static void setAllowInvalidUuid(boolean allowInvalidUuid) {
		LeopardValidUtil.allowInvalidUuid = allowInvalidUuid;
	}

	/**
	 * 是否合法的uuid
	 * 
	 * @param uuid
	 * @return
	 */
	public static boolean isUuid(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			return false;
		}
		if (allowInvalidUuid) {
			return true;
		}
		return uuid.matches(ID_REGEX);
	}
}
