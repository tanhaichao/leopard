package io.leopard.burrow.lang;

import java.util.List;

import io.leopard.burrow.util.DateTime;
import io.leopard.burrow.util.ListUtil;
import io.leopard.core.exception.ForbiddenException;
import io.leopard.core.exception.forbidden.IpForbiddenException;
import io.leopard.core.exception.invalid.DateInvalidException;
import io.leopard.core.exception.invalid.DateTimeInvalidException;
import io.leopard.core.exception.invalid.IpInvalidException;
import io.leopard.core.exception.invalid.PassportInvalidException;
import io.leopard.core.exception.invalid.UidInvalidException;
import io.leopard.core.exception.invalid.UsernameInvalidException;
import io.leopard.core.exception.other.NotLoginException;

/**
 * 参数合法性判断(出错抛异常)..
 * 
 * @author 阿海
 * 
 */
public class LeopardCheckUtil {

	// private static final Log logger = LogFactory.getLog(LeopardCheckUtil.class);

	/**
	 * 是否合法的用户名..
	 * 
	 * @param username
	 */
	public static void isUsername(String username) {
		if (!LeopardValidUtil.isValidUsername(username)) {
			throw new UsernameInvalidException(username);
		}
	}

	/**
	 * 判断用户是否已登录..
	 * 
	 * @param username
	 * @param proxyIp
	 */
	public static void isLogined(String username, String proxyIp) {
		if (username == null || username.length() == 0) {
			throw new NotLoginException("您[" + proxyIp + "]没有登录?");
		}
	}

	/**
	 * 判断用户是否允许访问..
	 * 
	 * @param allow
	 * @param username
	 * @throws ForbiddenException
	 */
	public static void isAllowByUsername(boolean allow, String username) throws ForbiddenException {
		if (!allow) {
			throw new ForbiddenException("您[" + username + "]无权访问.");
		}
	}

	/**
	 * 判断IP是否允许访问..
	 * 
	 * @param allow
	 * @param ip
	 * @throws IpForbiddenException
	 */
	public static void isAllowByIp(boolean allow, String ip) throws IpForbiddenException {
		if (!allow) {
			throw new IpForbiddenException("您[" + ip + "]无权访问.");
		}
	}

	/**
	 * 是否合法的用户名..
	 * 
	 * @param username
	 */
	public static void isValidUsername(String username) {
		if (!LeopardValidUtil.isValidUsername(username)) {
			throw new UsernameInvalidException(username);
		}
	}

	/**
	 * 是否合法的YYUID..
	 * 
	 * @param username
	 */
	public static void isValidUid(long uid) {
		if (!LeopardValidUtil.isValidUid(uid)) {
			throw new UidInvalidException(uid);
		}
	}

	/**
	 * 是否合法的passport .
	 * 
	 * @param passport
	 */
	public static void isValidPassport(String passport) {
		if (!LeopardValidUtil.isValidPassport(passport)) {
			throw new PassportInvalidException(passport);
		}
	}

	/**
	 * 判断是否合法的json .
	 * 
	 * @param json
	 */
	public static void isValidJson(String json) {
		if (json == null || json.length() == 0) {
			throw new NullPointerException("参数json不能为空.");
		}
	}

	/**
	 * 判断是否有效的List .
	 * 
	 * @param list
	 */
	public static void isValidList(List<?> list) {
		if (ListUtil.isEmpty(list)) {
			throw new NullPointerException("怎么会有空list传入?");
		}
	}

	/**
	 * 验证IP .
	 * 
	 * @param ip
	 */
	@Deprecated
	public static void checkIp(String ip) {
		if (!LeopardValidUtil.isValidIp(ip)) {
			throw new IpInvalidException(ip);
		}
	}

	/**
	 * 验证IP .
	 * 
	 * @param ip
	 */
	public static void isValidIp(String ip) {
		if (!LeopardValidUtil.isValidIp(ip)) {
			throw new IpInvalidException(ip);
		}
	}

	/**
	 * 验证用户名单 .
	 * 
	 * @param usernameList
	 */
	public static void isValidUsernameList(List<String> usernameList) {
		if (ListUtil.isEmpty(usernameList)) {
			throw new NullPointerException("用户名单不能为空.");
		}
		// 用户名合法性检查
		for (String username : usernameList) {
			if (!LeopardValidUtil.isValidUsername(username)) {
				throw new UsernameInvalidException(username);
			}
		}
	}

	/**
	 * 是否合法的请求页数..
	 * 
	 * @param pageid
	 */
	public static void isValidPageId(Integer pageId) {
		if (pageId == null || pageId <= 0) {
			throw new IllegalArgumentException("非法参数pageId[" + pageId + "].");
		}
	}

	/**
	 * 是否合法的日期格式..
	 * 
	 * @param date
	 */
	public static void isDate(String date) {
		if (!DateTime.isDate(date)) {
			throw new DateInvalidException(date);
		}
	}

	/**
	 * 是否合法的分页参数..
	 * 
	 * @param start
	 * @param size
	 */
	public static void isValidPageParameter(int start, int size) {
		if (start < 0) {
			throw new IllegalArgumentException("非法参数start[" + start + "].");
		}
		if (size <= 0) {
			throw new IllegalArgumentException("非法参数size[" + size + "].");
		}
	}

	/**
	 * 判断是否合法的日期时间格式..
	 * 
	 * @param datetime
	 */
	public static void isValidDateTime(String datetime) {
		if (!LeopardValidUtil.isValidDateTime(datetime)) {
			throw new DateTimeInvalidException(datetime);
		}
	}

}
