package io.leopard.mvc.trynb;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class TrynbLoggerImpl implements TrynbLogger {
	protected Log logger = LogFactory.getLog(TrynbServiceImpl.class);

	@Override
	public void error(HttpServletRequest request, String uri, Exception exception) {
		String proxyIp = TrynbUtil.getProxyIp(request);
		String referer = request.getHeader("referer");
		String username = TrynbUtil.getCookie("username", request);

		StringBuilder sb = new StringBuilder();
		sb.append("uri:").append(uri).append(" message:").append(exception.getMessage());
		{
			sb.append("\nproxyIp:").append(proxyIp);
			sb.append(" username:").append(username);
			sb.append(" referer:").append(referer);
		}
		sb.append("\n").append(request.getMethod());
		sb.append(" ").append("http://").append(request.getServerName()).append(uri);

		String queryString = request.getQueryString();
		if (!StringUtils.isEmpty(queryString)) {
			sb.append("?").append(queryString);
		}
		// return sb.toString();
		String clientInfo = sb.toString();
		logger.error(clientInfo, exception);
	}

	@Override
	public void warn(HttpServletRequest request, String uri, Exception exception) {
		logger.warn("uri:" + uri + " message:" + exception.getMessage());
	}

	@Override
	public void info(HttpServletRequest request, String uri, Exception exception) {
		logger.info("uri:" + uri + " message:" + exception.getMessage());
	}

	@Override
	public void debug(HttpServletRequest request, String uri, Exception exception) {
		logger.debug("uri:" + uri + " message:" + exception.getMessage());
	}

}
