package io.leopard.web.mvc.condition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

public class ServerNameRequestCondition extends AbstractRequestCondition<ServerNameRequestCondition> {

	protected Log logger = LogFactory.getLog(this.getClass());

	private final static ServerNameRequestCondition PRE_FLIGHT_MATCH = new ServerNameRequestCondition(null);

	private final Set<HeaderExpression> expressions;

	/**
	 * Create a new instance from the given header expressions. Expressions with header names 'Accept' or 'Content-Type' are ignored. See {@link ConsumesRequestCondition} and
	 * {@link ProducesRequestCondition} for those.
	 * 
	 * @param headers media type expressions with syntax defined in {@link RequestMapping#headers()}; if 0, the condition will match to every request
	 */
	public ServerNameRequestCondition(ExtensiveDomain extensiveDomain, String... headers) {
		this(parseExpressions(extensiveDomain, headers));
	}

	private ServerNameRequestCondition(Collection<HeaderExpression> conditions) {
		this.expressions = Collections.unmodifiableSet(new LinkedHashSet<HeaderExpression>(conditions));
	}

	private static Collection<HeaderExpression> parseExpressions(ExtensiveDomain extensiveDomain, String... headers) {
		Set<HeaderExpression> expressions = new LinkedHashSet<HeaderExpression>();
		if (headers != null) {
			for (String header : headers) {
				HeaderExpression expr = new HeaderExpression(extensiveDomain, header);
				if ("Accept".equalsIgnoreCase(expr.name) || "Content-Type".equalsIgnoreCase(expr.name)) {
					continue;
				}
				expressions.add(expr);
			}
		}
		return expressions;
	}

	/**
	 * Return the contained request header expressions.
	 */
	public Set<NameValueExpression<String>> getExpressions() {
		return new LinkedHashSet<NameValueExpression<String>>(this.expressions);
	}

	@Override
	protected Collection<HeaderExpression> getContent() {
		return this.expressions;
	}

	@Override
	protected String getToStringInfix() {
		return " && ";
	}

	/**
	 * Returns a new instance with the union of the header expressions from "this" and the "other" instance.
	 */
	@Override
	public ServerNameRequestCondition combine(ServerNameRequestCondition other) {
		// new Exception().printStackTrace();
		// logger.info("combine:" + expressions + " headers:" + StringUtils.join(headers, ","));
		Set<HeaderExpression> set = new LinkedHashSet<HeaderExpression>(this.expressions);
		set.addAll(other.expressions);
		return new ServerNameRequestCondition(set);
	}

	/**
	 * Returns "this" instance if the request matches all expressions; or {@code null} otherwise.
	 */
	@Override
	public ServerNameRequestCondition getMatchingCondition(HttpServletRequest request) {
		// logger.info("getMatchingCondition:" + request.getRequestURI());
		if (CorsUtils.isPreFlightRequest(request)) {
			return PRE_FLIGHT_MATCH;
		}
		for (HeaderExpression expression : expressions) {
			if (!expression.match(request)) {
				return null;
			}
		}
		return this;
	}

	/**
	 * Returns:
	 * <ul>
	 * <li>0 if the two conditions have the same number of header expressions
	 * <li>Less than 0 if "this" instance has more header expressions
	 * <li>Greater than 0 if the "other" instance has more header expressions
	 * </ul>
	 * <p>
	 * It is assumed that both instances have been obtained via {@link #getMatchingCondition(HttpServletRequest)} and each instance contains the matching header expression only or is otherwise empty.
	 */
	@Override
	public int compareTo(ServerNameRequestCondition other, HttpServletRequest request) {
		// logger.info("compareTo:" + request.getRequestURI());
		return other.expressions.size() - this.expressions.size();
	}

	/**
	 * Parses and matches a single header expression to a request.
	 */
	static class HeaderExpression extends AbstractNameValueExpression<String> {

		protected final List<String> valueList = new ArrayList<String>();
		private ExtensiveDomain extensiveDomain;

		public HeaderExpression(ExtensiveDomain extensiveDomain, String expression) {
			super(expression);
			this.extensiveDomain = extensiveDomain;
			String[] values = value.split(",");
			for (int i = 0; i < values.length; i++) {
				values[i] = values[i].trim();
				valueList.add(values[i]);
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
			// System.err.println("matchName name:" + name + " request:" + request.getHeader("*.onloon.co"));
			return request.getHeader(name) != null;
		}

		@Override
		protected boolean matchValue(HttpServletRequest request) {
			String serverName = request.getServerName();
			// System.err.println("matchValue:" + serverName);
			boolean contains = valueList.contains(serverName);
			if (contains) {
				return true;
			}

			if (extensiveDomain != null) {
				boolean match = extensiveDomain.match(serverName);
				if (match) {
					return true;
				}
			}
			return false;
		}
	}

}
