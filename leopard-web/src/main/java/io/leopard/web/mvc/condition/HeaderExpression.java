package io.leopard.web.mvc.condition;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.vhost.ExtensiveDomain;

public class HeaderExpression extends AbstractNameValueExpression<String> {

	protected Log logger = LogFactory.getLog(this.getClass());

	protected final List<String> valueList = new ArrayList<String>();

	private ExtensiveDomain extensiveDomain;

	public HeaderExpression(ExtensiveDomain extensiveDomain, String expression) {
		super(expression);
		this.extensiveDomain = extensiveDomain;
		if (value != null) {
			String[] values = value.split(",");
			for (int i = 0; i < values.length; i++) {
				values[i] = values[i].trim();
				valueList.add(values[i]);
			}
		}
	}

	@Override
	protected boolean isCaseSensitiveName() {
		return false;
	}

	@Override
	protected String parseValue(String valueExpression) {
		return valueExpression;
	}

	@Override
	protected boolean matchName(HttpServletRequest request) {
		// logger.info("matchName name:" + name + " request:" + request.getHeader(name));
		if ("Host".equals(name)) {
			return matchExtensiveDomain(request);
		}
		return request.getHeader(name) != null;
	}

	/**
	 * 匹配泛域名
	 * 
	 * @param request
	 * @return
	 */
	protected boolean matchExtensiveDomain(HttpServletRequest request) {
		if (extensiveDomain != null) {
			String serverName = request.getServerName();
			boolean match = extensiveDomain.match(serverName);
			if (match) {
				return true;
			}
		}
		return false;

	}

	@Override
	protected boolean matchValue(HttpServletRequest request) {
		String serverName = request.getServerName();
		boolean contains = valueList.contains(serverName);
		// logger.info("matchValue:" + serverName + " contains:" + contains);
		if (contains) {
			return true;
		}
		return matchExtensiveDomain(request);
	}
}