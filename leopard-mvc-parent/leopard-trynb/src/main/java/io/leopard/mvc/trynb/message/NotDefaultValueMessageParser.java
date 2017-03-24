package io.leopard.mvc.trynb.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 没有默认值
 * 
 * @author 谭海潮
 *
 */
public class NotDefaultValueMessageParser implements MessageParser {

	// Field 'reason' doesn't have a default value
	@Override
	public String parse(String message) {
		String regex = "Field '(.*?)' doesn't have a default value";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find()) {
			String columnName = m.group(1);
			return "字段" + columnName + "没有默认值.";
		}
		return null;
	}
}