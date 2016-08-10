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

public class LeopardHeadersRequestCondition extends AbstractRequestCondition<LeopardHeadersRequestCondition> {

	protected Log logger = LogFactory.getLog(this.getClass());

	private final static LeopardHeadersRequestCondition PRE_FLIGHT_MATCH = new LeopardHeadersRequestCondition();

	private final Set<HeaderExpression> expressions;

	/**
	 * Create a new instance from the given header expressions. Expressions with header names 'Accept' or 'Content-Type' are ignored. See {@link ConsumesRequestCondition} and
	 * {@link ProducesRequestCondition} for those.
	 * 
	 * @param headers media type expressions with syntax defined in {@link RequestMapping#headers()}; if 0, the condition will match to every request
	 */
	public LeopardHeadersRequestCondition(String... headers) {
		this(parseExpressions(headers));
	}

	private LeopardHeadersRequestCondition(Collection<HeaderExpression> conditions) {
		this.expressions = Collections.unmodifiableSet(new LinkedHashSet<HeaderExpression>(conditions));
	}

	private static Collection<HeaderExpression> parseExpressions(String... headers) {
		Set<HeaderExpression> expressions = new LinkedHashSet<HeaderExpression>();
		if (headers != null) {
			for (String header : headers) {
				HeaderExpression expr = new HeaderExpression(header);
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
	public LeopardHeadersRequestCondition combine(LeopardHeadersRequestCondition other) {
		// new Exception().printStackTrace();
		// logger.info("combine:" + expressions + " headers:" + StringUtils.join(headers, ","));
		Set<HeaderExpression> set = new LinkedHashSet<HeaderExpression>(this.expressions);
		set.addAll(other.expressions);
		return new LeopardHeadersRequestCondition(set);
	}

	/**
	 * Returns "this" instance if the request matches all expressions; or {@code null} otherwise.
	 */
	@Override
	public LeopardHeadersRequestCondition getMatchingCondition(HttpServletRequest request) {
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
	public int compareTo(LeopardHeadersRequestCondition other, HttpServletRequest request) {
		logger.info("compareTo:" + request.getRequestURI());
		return other.expressions.size() - this.expressions.size();
	}

	/**
	 * Parses and matches a single header expression to a request.
	 */
	static class HeaderExpression extends AbstractNameValueExpression<String> {

		protected final List<String> valueList = new ArrayList<String>();

		public HeaderExpression(String expression) {
			super(expression);
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
			return request.getHeader(name) != null;
		}

		@Override
		protected boolean matchValue(HttpServletRequest request) {
			String value2 = request.getHeader(name);
			System.err.println("matchValue name:" + name + " value:" + value + " value2:" + value2);
			// return value.indexOf("," + value2 + ",") != -1;
			return valueList.contains(value2);
		}
	}

}
