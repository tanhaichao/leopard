package io.leopard.burrow.lang;

import io.leopard.burrow.util.DateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * 验证yyuid是否合法 .
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

	/**
	 * 验证imid是否合法 .
	 * 
	 * @param imid
	 * @return 合法 false 非法true
	 */
	public static boolean isValidImid(long imid) {
		if (imid <= 0) {
			return false;
		}
		return true;
	}

	private static Pattern GAMEID_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

	/**
	 * 验证gameid是否合法 .
	 * 
	 * @param gameId
	 * @return
	 */
	public static boolean isValidGameId(String gameId) {
		// return StringUtils.isNotEmpty(gameId);
		if (gameId == null || gameId.length() == 0) {
			return false;
		}
		Matcher m = GAMEID_PATTERN.matcher(gameId);
		return m.find();
	}

	private static Pattern SERVERID_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

	/**
	 * 验证serverid是否合法 .
	 * 
	 * @param serverId
	 * @return
	 */
	public static boolean isValidServerId(String serverId) {
		// return StringUtils.isNotEmpty(serverId);
		if (serverId == null || serverId.length() == 0) {
			return false;
		}
		Matcher m = SERVERID_PATTERN.matcher(serverId);
		return m.find();
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
}
