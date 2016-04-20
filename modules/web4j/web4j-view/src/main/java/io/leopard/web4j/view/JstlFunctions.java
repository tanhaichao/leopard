package io.leopard.web4j.view;

import java.util.Calendar;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.util.JavaScriptUtils;

public class JstlFunctions {
	public static final int HIGHEST_SPECIAL = '>';
	public static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];
	static {
		specialCharactersRepresentation['&'] = "&amp;".toCharArray();
		specialCharactersRepresentation['<'] = "&lt;".toCharArray();
		specialCharactersRepresentation['>'] = "&gt;".toCharArray();
		specialCharactersRepresentation['"'] = "&#034;".toCharArray();
		specialCharactersRepresentation['\''] = "&#039;".toCharArray();
	}

	private static final Log logger = LogFactory.getLog(JstlFunctions.class);

	/**
	 * 对字符串进行转义.
	 * 
	 * @param input
	 *            字符串
	 * @return 转义后的字符串
	 */
	public static String escape(String input) {
		return escapeXml(input);
	}

	public static String escapeXml(String input) {
		// copty org.apache.taglibs.standard.functions.Functions.escapeXml(input);
		if (input == null) {
			return "";
		}
		int start = 0;
		int length = input.length();
		char[] arrayBuffer = input.toCharArray();
		StringBuffer escapedBuffer = null;

		for (int i = 0; i < length; i++) {
			char c = arrayBuffer[i];
			if (c <= HIGHEST_SPECIAL) {
				char[] escaped = specialCharactersRepresentation[c];
				if (escaped != null) {
					// create StringBuffer to hold escaped xml string
					if (start == 0) {
						escapedBuffer = new StringBuffer(length + 5);
					}
					// add unescaped portion
					if (start < i) {
						escapedBuffer.append(arrayBuffer, start, i - start);
					}
					start = i + 1;
					// add escaped xml
					escapedBuffer.append(escaped);
				}
			}
		}
		// no xml escaping was necessary
		if (start == 0) {
			return input;
		}
		// add rest of unescaped portion
		if (start < length) {
			escapedBuffer.append(arrayBuffer, start, length - start);
		}
		return escapedBuffer.toString();
	}

	/**
	 * 不对字符串进行转义.
	 * 
	 * @param input
	 *            字符串
	 * @return 字符串
	 */
	public static String noescape(String input) {
		return input;
	}

	/**
	 * 对javascript特殊字符进行转义.
	 * 
	 * @param str
	 *            javascript文本
	 * @return 转义后的字符串
	 */
	public static String escapeJavascript(String str) {
		return JavaScriptUtils.javaScriptEscape(str);
	}

	/**
	 * 对javascript变量进行转义.
	 * 
	 * @param str
	 *            js变量
	 * @return 转义后的字符串
	 */
	public static String escapeJavascriptParam(String str) {
		if (str == null) {
			return null;
		}
		if (str.indexOf('"') != -1) {
			Exception e = new Exception("invalid js param:" + str);
			logger.error(e.getMessage(), e);
			str = str.replace("\"", "");
		}
		return JavaScriptUtils.javaScriptEscape(str);
	}

	/**
	 * 对html标签进行转义.
	 * 
	 * @param str
	 *            字符串
	 * @return 转义后的字符串
	 */
	public static String escapeHTMLTags(final String str) {
		if (str == null) {
			return null;
		}
		// 替换时先判断是否存在需要替换的字符,提高性能
		if (str.indexOf('<') == -1 && str.indexOf('>') == -1 && str.indexOf('"') == -1) {
			return str;
		}

		int stringLength = str.length();
		// StringBuilder buf = new StringBuilder();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = str.charAt(i);

			switch (c) {
			case '<':
				buf.append("&lt;");
				break;
			case '>':
				buf.append("&gt;");
				break;
			case '"':
				buf.append("&quot;");
				break;
			default:
				buf.append(c);
			}
		}
		return buf.toString();
	}

	/**
	 * 布尔值转成int.
	 * 
	 * @param flag
	 * @return
	 */
	public static Integer b2i(final Boolean flag) {
		if (flag) {
			return 1;
		}
		else {
			return 0;
		}
	}

	/**
	 * 获取秒数.
	 * 
	 * @param datetime
	 * @return
	 */
	public static Integer unixTime(final String datetime) {
		long timestamp = timestamp(datetime);
		int time = (int) (timestamp / 1000);
		return time;
	}

	/**
	 * 获取时间戳.
	 * 
	 * @param datetime
	 * @return
	 */
	public static Long timestamp(final String datetime) {
		// 2009-10-10 01:01:01.1
		// if (!DateTime.isDateTime(datetime)) {
		// return 1;
		// }

		Calendar cal = Calendar.getInstance();

		int year = Integer.parseInt(datetime.substring(0, 4));
		int month = Integer.parseInt(datetime.substring(5, 7));
		int day = Integer.parseInt(datetime.substring(8, 10));

		int hour = Integer.parseInt(datetime.substring(11, 13));
		int minute = Integer.parseInt(datetime.substring(14, 16));
		int second = Integer.parseInt(datetime.substring(17, 19));

		// System.out.println(year + ":" + month + ":" + day);
		// System.out.println(hour + ":" + minute + ":" + second);
		cal.set(year, month - 1, day, hour, minute, second);
		if (datetime.length() > 19) {
			int mill = Integer.parseInt(datetime.substring(20));
			cal.set(Calendar.MILLISECOND, mill);
		}
		else {
			cal.set(Calendar.MILLISECOND, 0);
		}

		long timestamp = cal.getTimeInMillis();
		return timestamp;
	}

	static String regex = "(http|ftp|https)://[^\u4e00-\u9fa5]*";
	static Pattern p = Pattern.compile(regex);

	/**
	 * 转换空格和换行符号.
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceSpace(String content) {
		if (content == null) {
			return null;
		}
		if (true) {
			// TODO 特殊实现，客户端传递的特殊字符
			char c = 8233;
			content.replace(c, '\n');
		}
		content = content.replace("  ", "　");
		content = content.replace("\r", "");
		content = content.replace("\n", "<br/>");

		return content;
	}

	/**
	 * 获取空格.
	 * 
	 * @param length
	 *            空格数目
	 * @return
	 */
	public static String space(Integer length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append("&nbsp;");
		}
		return sb.toString();
	}

}
