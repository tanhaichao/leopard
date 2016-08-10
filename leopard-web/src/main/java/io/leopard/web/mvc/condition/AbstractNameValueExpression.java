package io.leopard.web.mvc.condition;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.NameValueExpression;

public abstract class AbstractNameValueExpression<T> implements NameValueExpression<T> {

	protected final String name;

	protected final T value;

	protected final boolean isNegated;

	AbstractNameValueExpression(String expression) {
		int separator = expression.indexOf('=');
		if (separator == -1) {
			this.isNegated = expression.startsWith("!");
			this.name = isNegated ? expression.substring(1) : expression;
			this.value = null;
		}
		else {
			this.isNegated = (separator > 0) && (expression.charAt(separator - 1) == '!');
			this.name = isNegated ? expression.substring(0, separator - 1) : expression.substring(0, separator);
			this.value = parseValue(expression.substring(separator + 1));
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public boolean isNegated() {
		return this.isNegated;
	}

	protected abstract boolean isCaseSensitiveName();

	protected abstract T parseValue(String valueExpression);

	public final boolean match(HttpServletRequest request) {
		boolean isMatch;
		if (this.value != null) {
			isMatch = matchValue(request);
		}
		else {
			isMatch = matchName(request);
		}
		return isNegated ? !isMatch : isMatch;
	}

	protected abstract boolean matchName(HttpServletRequest request);

	protected abstract boolean matchValue(HttpServletRequest request);

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof AbstractNameValueExpression) {
			AbstractNameValueExpression<?> other = (AbstractNameValueExpression<?>) obj;
			String thisName = isCaseSensitiveName() ? this.name : this.name.toLowerCase();
			String otherName = isCaseSensitiveName() ? other.name : other.name.toLowerCase();
			return ((thisName.equalsIgnoreCase(otherName)) && (this.value != null ? this.value.equals(other.value) : other.value == null) && this.isNegated == other.isNegated);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = isCaseSensitiveName() ? name.hashCode() : name.toLowerCase().hashCode();
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (isNegated ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (value != null) {
			builder.append(name);
			if (isNegated) {
				builder.append('!');
			}
			builder.append('=');
			builder.append(value);
		}
		else {
			if (isNegated) {
				builder.append('!');
			}
			builder.append(name);
		}
		return builder.toString();
	}
}