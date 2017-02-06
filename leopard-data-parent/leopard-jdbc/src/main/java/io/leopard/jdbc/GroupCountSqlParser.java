package io.leopard.jdbc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupCountSqlParser extends CountSqlParser {

	public GroupCountSqlParser(String sql, StatementParameter param) {
		super(parseGroupSql(sql), param);
	}

	private static Pattern p = Pattern.compile("group by ([a-zA-Z_0-9]+)");

	protected static String parseGroupSql(String sql) {
		Matcher m = p.matcher(sql);
		if (!m.find()) {
			return sql;
		}
		String fieldName = m.group(1);

		StringBuffer sb = new StringBuffer();
		m.appendReplacement(sb, "");
		m.appendTail(sb);
		sql = sb.toString();

		sql = sql.replaceAll("select .*? from", "select count(distinct(" + fieldName + ")) from");
		sql = sql.replaceAll("SELECT .*? FROM", "SELECT count(distinct(" + fieldName + ")) FROM");

		// System.out.println("fieldName:" + fieldName + " sql:" + sql);
		return sql;
	}

	protected void parse() {
		String sql = this.sql;
		if (sql.indexOf("count(") == -1) {
			super.parse();
			return;
		}
		this.parsePostfix(sql);
	}

}
