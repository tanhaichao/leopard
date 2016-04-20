package io.leopard.web4j.nobug.xss;

import org.apache.commons.logging.Log;

public class XssFilterImpl implements XssFilter {
	// protected static Log logger = LogFactory.getLog(XssFilterImpl.class);

	@Override
	public String filter(Log logger, String body) {
		boolean hasXss = XssCheckerImpl.getInstance().check(body);
		if (hasXss) {
			logger.error("body has xss:" + body);
			body = "body包含XSS相关字符串.";
		}
		return body;
	}
}
