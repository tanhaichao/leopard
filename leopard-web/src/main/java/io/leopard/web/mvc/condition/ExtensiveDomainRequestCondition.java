package io.leopard.web.mvc.condition;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;

import io.leopard.vhost.ExtensiveDomain;

public class ExtensiveDomainRequestCondition extends AbstractRequestCondition<ExtensiveDomainRequestCondition> {

	protected Log logger = LogFactory.getLog(this.getClass());

	private final static ExtensiveDomainRequestCondition PRE_FLIGHT_MATCH = new ExtensiveDomainRequestCondition((ExtensiveDomain) null);

	private final Set<HeaderExpression> expressions;

	public ExtensiveDomainRequestCondition(ExtensiveDomain extensiveDomain, String... headers) {
		this(parseExpressions(extensiveDomain, headers));
		// logger.info("ServerNameRequestCondition extensiveDomain:" + extensiveDomain + " headers:" + StringUtils.join(headers, ", "));
	}

	private ExtensiveDomainRequestCondition(Collection<HeaderExpression> conditions) {
		// logger.info("ServerNameRequestCondition conditions:" + conditions);
		this.expressions = Collections.unmodifiableSet(new LinkedHashSet<HeaderExpression>(conditions));
	}

	private static Collection<HeaderExpression> parseExpressions(ExtensiveDomain extensiveDomain, String... headers) {
		// TODO
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
		if (!ArrayUtils.contains(headers, "Host")) {
			HeaderExpression expr = new HeaderExpression(extensiveDomain, "Host");
			expressions.add(expr);
		}
		return expressions;
	}

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

	@Override
	public ExtensiveDomainRequestCondition combine(ExtensiveDomainRequestCondition other) {
		// new Exception().printStackTrace();
		// logger.info("combine:" + expressions + " headers:" + other.getClass().getName());
		Set<HeaderExpression> set = new LinkedHashSet<HeaderExpression>(this.expressions);
		set.addAll(other.expressions);
		return new ExtensiveDomainRequestCondition(set);
	}

	@Override
	public ExtensiveDomainRequestCondition getMatchingCondition(HttpServletRequest request) {
		// logger.info("getMatchingCondition:" + request.getRequestURI());
		if (CorsUtils.isPreFlightRequest(request)) {
			return PRE_FLIGHT_MATCH;
		}
		// logger.info("getMatchingCondition expressions:" + expressions);
		for (HeaderExpression expression : expressions) {
			if (!expression.match(request)) {
				return null;
			}
		}
		return this;
	}

	@Override
	public int compareTo(ExtensiveDomainRequestCondition other, HttpServletRequest request) {
		// logger.info("compareTo:" + request.getRequestURI());
		return other.expressions.size() - this.expressions.size();
	}

}
