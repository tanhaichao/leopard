package io.leopard.web4j.trynb;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.core.exception.LeopardException;
import io.leopard.core.exception.LeopardRuntimeException;
import io.leopard.core.exception.other.OutSideException;
import io.leopard.web4j.servlet.CookieUtil;
import io.leopard.web4j.servlet.RequestUtil;

import java.lang.reflect.GenericSignatureFormatError;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class ErrorUtil {
	protected static final Log logger = LogFactory.getLog(ErrorUtil.class);

	/**
	 * 获取异常信息.
	 * 
	 * @param e
	 * @return
	 */
	public static String parseMessage(Throwable e) {
		AssertUtil.assertNotNull(e, "exception不能为空?");
		if (e instanceof org.springframework.dao.DataAccessException) {
			return "操作数据库出错，请稍后重试.";
		}
		if (e instanceof SQLException) {
			return "操作数据库出错，请稍后重试.";
		}
		if (e instanceof JedisConnectionException) {
			return "操作数据库出错，请稍后重试.";
		}
		if (e instanceof OutSideException) {
			return "访问外部接口出错，请稍后重试.";
		}
		if (e instanceof GenericSignatureFormatError) {
			return "更新程序后，还没有重启服务.";
		}
		if (e instanceof IllegalArgumentException) {
			return replaceMessage(e);
		}
		if (e instanceof LeopardRuntimeException) {
			return replaceMessage(e);
		}
		if (e instanceof LeopardException) {
			return replaceMessage(e);
		}
		if (e instanceof RuntimeException) {
			return replaceMessage(e);
		}
		return "未知错误.";
		// String message = e.getMessage();
		// return message;
	}

	/**
	 * 替换异常信息.
	 * 
	 * @param e
	 * @return
	 */
	public static String replaceMessage(Throwable e) {
		String message = e.getMessage();
		return replaceMessage(message);
	}

	public static String replaceMessage(String message) {
		if (message == null) {
			return null;
		}
		String message2 = message.replaceAll("\\[.*?\\]", "");

		if (message2.indexOf("<") != -1 || message2.indexOf(">") != -1) {
			logger.info("error message has html:" + message);
			return "错误消息有HTML代码.";
		}

		// FIXME ahai 没有做XSS判断
		// boolean hasXss = XssCheckerScriptImpl.getInstance().check(message2);
		// if (hasXss) {
		// logger.info("error message has xss:" + message);
		// return "错误消息有XSS风险.";
		// }

		return message2;
	}

	// /**
	// * 是否使用freemarker?.
	// *
	// * @return
	// */
	// public static boolean isUseFtl() {
	// Object bean =
	// LeopardBeanFactoryAware.getBeanFactory().getBean("viewResolver");
	// if (bean == null) {
	// return false;
	// }
	//
	// if (bean instanceof FreeMarkerViewResolver) {
	// return true;
	// }
	// return false;
	// }

	public static void error(Log logger, String message, Throwable t) {
		HttpServletRequest request = RequestUtil.getCurrentRequest();
		// String uri = request.getRequestURI();
		String uri = RequestUtil.getRequestContextUri(request);
		String clientInfo = getClientInfo(request, uri, message);
		logger.error(clientInfo, t);
	}

	public static boolean match(String type, String exceptionClassName) {
		if (type.indexOf(".") == -1) {
			if (exceptionClassName.endsWith(type)) {
				return true;
			}
		}
		if (exceptionClassName.equals(type)) {
			return true;
		}
		return false;
	}

	public static String getClientInfo(HttpServletRequest request, String uri, String message) {
		String proxyIp = RequestUtil.getProxyIp(request);
		String referer = request.getHeader("referer");
		String username = CookieUtil.getCookie("username", request);

		StringBuilder sb = new StringBuilder();
		sb.append("uri:").append(uri).append(" message:").append(message);
		{
			sb.append("\nproxyIp:").append(proxyIp);
			sb.append(" username:").append(username);
			sb.append(" referer:").append(referer);
		}
		sb.append("\n").append(request.getMethod());
		sb.append(" ").append("http://").append(request.getServerName()).append(uri);

		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			sb.append("?").append(queryString);
		}
		return sb.toString();
	}
}
