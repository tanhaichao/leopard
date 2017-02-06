package io.leopard.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class CountSqlParser {

	protected final String sql;
	private final StatementParameter param;

	protected String countSql;

	private Integer start;
	private Integer size;

	public CountSqlParser(String sql, StatementParameter param) {
		this.sql = sql;
		// System.err.println("sql:" + sql);
		this.param = param;
		this.parse();
	}

	private static final Pattern LIMIT_PATTERN = Pattern.compile(" limit .*$", Pattern.CASE_INSENSITIVE);
	private static final Pattern ORDERBY_PATTERN = Pattern.compile(" order by .*$", Pattern.CASE_INSENSITIVE);

	protected void parse() {
		String sql = this.sql;
		sql = sql.replaceAll("select .*? from", "select count(*) from");
		sql = sql.replaceAll("SELECT .*? FROM", "SELECT count(*) FROM");
		this.parsePostfix(sql);
	}

	protected void parsePostfix(String sql) {
		{
			Matcher m = ORDERBY_PATTERN.matcher(sql);
			if (m.find()) {
				sql = sql.substring(0, m.start());
			}
		}
		{
			// 这里在orderby解析后面使用else
			Matcher m = LIMIT_PATTERN.matcher(sql);

			if (m.find()) {
				int index = m.start();
				sql = sql.substring(0, index);

			}
		}
		parseLimitValue();
		this.countSql = sql;
		// System.err.println("countSql2:" + countSql);
	}

	/**
	 * 解析start和size值
	 */
	protected void parseLimitValue() {
		Matcher m = LIMIT_PATTERN.matcher(sql);
		if (!m.find()) {
			return;
		}
		int index = m.start();
		String limitSql = sql.substring(index);
		// System.out.println("limitSql:" + limitSql);
		int count = StringUtils.countOccurrencesOf(limitSql, "?");

		int paramCount = param.size();
		if (count == 1) {
			this.size = param.getInt(paramCount - 1);
		}
		else if (count == 2) {
			this.start = param.getInt(paramCount - 2);
			this.size = param.getInt(paramCount - 1);
		}
		else {
			throw new IllegalArgumentException("怎么limit参数是" + count + "个?");
		}
	}

	public String getCountSql() {
		return this.countSql;
	}

	public StatementParameter getCountParam() {
		int max = StringUtils.countOccurrencesOf(this.countSql, "?");
		Object[] values = this.param.getArgs();
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < max; i++) {
			Class<?> type = this.param.getType(i);
			param.setObject(type, values[i]);
		}
		return param;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getSize() {
		return size;
	}

}
