package io.leopard.jdbc.builder;

import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL解析器.
 * 
 * @author 阿海
 * 
 */
public class InsertParser extends AbstractSqlParser {
	private String sql;

	public InsertParser(String sql) {
		this.sql = sql;
	}

	public LinkedHashSet<String> getFields() {
		String regex = "(?i)insert[ ]*?(ignore[ ]*?)?into[ ]*?([a-zA-Z0-9_]+)\\((.*?)\\)[ ]*?value[s]?[ ]*?\\((.*)\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		if (!m.find()) {
			System.err.println("error insert sql:" + sql);
			throw new IllegalArgumentException("识别不了Insert SQL[" + sql + "]..");
		}
		String fieldZone = m.group(3);
		String valueZone = m.group(4);
		return this.parse(fieldZone, valueZone);
	}
}
