package io.leopard.web4j.nobug.xss;

import io.leopard.web4j.nobug.annotation.NoXss;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

/**
 * 参数值合法性检查.
 * 
 * 1、queryString中的参数值规则:"^[a-zA-Z0-9\\-:/_,\\.=]+$"，不符合规则抛异常<br/>
 * 2、特殊支持日期+时间格式:0000-00-00 00:00:00<br/>
 * 3、忽略有对应ParameterValidator的参数，Leopard默认的ParameterValidator有gameId、serverId、passport、username等<br/>
 * 4、如有复杂参数可以通过POST方式提交<br/>
 * 
 * 参数中的URL怎么办？</br>
 * 
 * @author 阿海
 * 
 */
public class XssUtil {
	protected static final Log logger = LogFactory.getLog(XssUtil.class);

	private static boolean enable = true;// 是否启用xss

	public static boolean isEnable() {
		return enable;
	}

	public static void setEnable(boolean enable) {
		XssUtil.enable = enable;
	}

	// TODO ahai 需要做线程同步吗?
	private static final Set<String> IGNORE_NAME_SET = new HashSet<String>();

	static {
		addIgnoreName("uname");
		addIgnoreName("resinIp");
	}

	public static void addIgnoreName(String name) {
		IGNORE_NAME_SET.add(name);
	}

	/**
	 * 判断request是否有xss攻击.
	 * 
	 * @param request
	 */
	public static boolean initXSS(HttpServletRequest request, Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		NoXss noXss = handlerMethod.getMethodAnnotation(NoXss.class);
		// System.err.println("initXSS uri:" + request.getRequestURI() + " noXss:" + noXss);
		if (noXss != null) {
			request.setAttribute(XssAttributeCheckUtil.IGNORE_XSS_ATTRIBUTE_NAME, true);
		}
		// MethodParameter[] params = handlerMethod.getMethodParameters();
		// for (MethodParameter param : params) {
		//
		// }
		return true;
	}

	public static void checkParameter(String name, String value) {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		if (IGNORE_NAME_SET.contains(name)) {
			return;
		}
		// System.err.println("checkParameter name:" + name + " value:" + value);
		// value = StringUtil.urlDecode(value);
		boolean hasXss = XssCheckerImpl.getInstance().check(value);
		if (hasXss) {
			logger.error("has xss name:" + name + " value:" + value);
			if (isValidName(name)) {
				throw new XssException("参数" + name + "值不合法.");
			}
			else {
				throw new XssException("参数名称[" + name + "]和值都不合法.");
			}
		}

	}

	// /**
	// * 检查参数是否已禁用XSS检查
	// *
	// * @param name
	// * @param handler
	// * @return
	// */
	// protected static boolean isNoXssParameter(String name, Object handler) {
	// if (handler == null) {
	// return false;
	// }
	//
	// HandlerMethod handlerMethod = (HandlerMethod) handler;
	// NoXss noXss = handlerMethod.getMethodAnnotation(NoXss.class);
	// return ObjectUtil.isNotNull(noXss);
	// }

	protected static Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

	protected static boolean isValidName(String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		Matcher m = NAME_PATTERN.matcher(name);
		return m.find();
	}

	public static void checkUrl(String url) {
		// url:http://message.game.yy.com/udb/callback.do?xparam=1&url=http://message.game.yy.com/admin/index.do
		// url:http://message.game.yy.com/udb/callback.do?cancel=1
		// url:http://message.game.yy.com/admin/index.do
		boolean hasXss = XssCheckerUrlImpl.getInstance().check(url);
		if (hasXss) {
			throw new XssException("非法URL参数[" + url + "]");
		}
		System.err.println("url:" + url);
		logger.info("url:" + url);
	}
}
