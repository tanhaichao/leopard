package io.leopard.mvc.trynb.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段太长
 * 
 * @author 谭海潮
 *
 */
public class TooLongMessageParser implements MessageParser {
	// Data truncation: Data too long for column 'spec' at row 1
	@Override
	public String parse(String message) {
		String regex = "Data truncation: Data too long for column '(.*?)' at row";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find()) {
			String columnName = m.group(1);
			return "字段" + columnName + "太长，请稍后重试.";
		}
		return null;
	}
}
