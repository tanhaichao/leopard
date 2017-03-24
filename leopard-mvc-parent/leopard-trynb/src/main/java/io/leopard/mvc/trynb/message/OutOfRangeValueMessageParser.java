package io.leopard.mvc.trynb.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字类型越界
 * 
 * @author 谭海潮
 *
 */
public class OutOfRangeValueMessageParser implements MessageParser {
	// [Data truncation: Out of range value for column 'weight' at row 1]

	@Override
	public String parse(String message) {
		String regex = "Out of range value for column '(.*?)' at row";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find()) {
			String columnName = m.group(1);
			return "数字类型字段" + columnName + "越界.";
		}
		return null;
	}
}