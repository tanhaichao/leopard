package io.leopard.jdbc.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL解析器.
 * 
 * @author 阿海
 * 
 */
public class UpdateParser {
	private String sql;

	public UpdateParser(String sql) {
		this.sql = sql;
	}

	public List<String> getFields() {
		String regex = "(?i)update[ ]*?([a-zA-Z0-9_]+)[ ]*?set(.*?) where ([^;]*)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		if (!m.find()) {
			System.err.println("error update sql:" + sql);
			throw new IllegalArgumentException("识别不了Update SQL[" + sql + "]..");
		}
		String setZone = m.group(2).trim();
		String whereZone = m.group(3).trim();
		// System.out.println("setZone:" + setZone);
		// System.out.println("whereZone:" + whereZone);
		List<String> setFieldList = this.parseFields(setZone);
		List<String> whereFieldList = this.parseFields(whereZone);

		List<String> list = new ArrayList<String>();
		list.addAll(setFieldList);
		list.addAll(whereFieldList);
		return list;
		// return null;
	}

	protected List<String> parseFields(String content) {
		String regex = "([a-zA-Z0-9_]+)[ ]*?=[ ]*?\\?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		List<String> list = new ArrayList<String>();
		while (m.find()) {
			String fieldName = m.group(1);
			System.out.println("fieldName:" + fieldName + " content:" + content);
			list.add(fieldName);
		}
		return list;
	}

}
