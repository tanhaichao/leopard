package io.leopard.burrow.util;

import io.leopard.burrow.lang.AssertUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
//import org.apache.commons.codec.binary.Base64;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * 字符串转换工具类
 * 
 * @author 阿海
 * 
 */
public class StringUtil {// NOPMD

	// /**
	// * Base64编码
	// *
	// * @param content
	// * 文本
	// * @return 编码后的文本
	// */
	// public static String encodeBase64(String content) {
	// // if (StringUtils.isEmpty(content)) {
	// // return content;
	// // }
	// // return new String(Base64.encodeBase64(content.getBytes()));
	// return Base64.encode(content);
	// }
	//
	// /**
	// * Base64解码
	// *
	// * @param content
	// * 文本
	// * @return 解码后的文本
	// */
	// public static String decodeBase64(String content) {
	// return Base64.decode(content);
	// }

	/**
	 * 功能描述：生成指定长度随机数字字符串key
	 * 
	 * @param len
	 * @return
	 */
	public static String generateNumberKey(int len) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			builder.append(RandomUtils.nextInt(10));
		}
		return builder.toString();
	}

	/**
	 * 对SQL语句进行转义
	 * 
	 * @param param SQL语句
	 * @return 转义后的字符串
	 */
	public static String escapeSQLParam(final String param) {
		int stringLength = param.length();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = param.charAt(i);
			switch (c) {
			case 0: /* Must be escaped for 'mysql' */
				buf.append('\\');
				buf.append('0');
				break;
			case '\n': /* Must be escaped for logs */
				buf.append('\\');
				buf.append('n');
				break;
			case '\r':
				buf.append('\\');
				buf.append('r');
				break;
			case '\\':
				buf.append('\\');
				buf.append('\\');
				break;
			case '\'':
				buf.append('\\');
				buf.append('\'');
				break;
			case '"': /* Better safe than sorry */
				buf.append('\\');
				buf.append('"');
				break;
			case '\032': /* This gives problems on Win32 */
				buf.append('\\');
				buf.append('Z');
				break;
			default:
				buf.append(c);
			}
		}
		return buf.toString();
	}

	/**
	 * 对字符串进行转义
	 * 
	 * @param content 文本
	 * @return 转义后的字符串
	 */
	public static String escapePattern(final String content) {// NOPMD
		if (content == null) {
			return null;
		}

		int stringLength = content.length();
		// StringBuilder buf = new StringBuilder();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = content.charAt(i);
			if (c == '\\' || c == '?' || c == '+' || c == '*' || c == '[' || c == ']' || c == '{' || c == '}' || c == '(' || c == ')' || c == '-' || c == '$' || c == '|') {
				buf.append('\\');
			}
			buf.append(c);
		}
		return buf.toString();
	}

	/**
	 * 对html标签进行转义
	 * 
	 * @param str 字符串
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
	 * 转换javascript参数</br>
	 * 将'换成\'</br>
	 * 将"转换成&quot;
	 * 
	 * @param str 需要转换的内容
	 * @return 转换后的内容
	 */
	public static String escapeJavascriptParam(final String str) {
		if (str == null) {
			return null;
		}
		// 替换时先判断是否存在需要替换的字符,提高性能
		if (str.indexOf('"') == -1 && str.indexOf('\'') == -1) {
			return str;
		}

		int stringLength = str.length();
		// StringBuilder buf = new StringBuilder();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = str.charAt(i);

			switch (c) {
			case '\'':
				buf.append("\\'");
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
	 * 如果字符串为空,返回null;否则，返回字符串</br>
	 * 
	 * @param str 字符串
	 * @return 字符串
	 */
	public static String emptyToNull(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		else {
			return str;
		}
	}

	/**
	 * 判断字符串1与字符串2是否不相等
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return 结果
	 */
	public static boolean notEquals(String str1, String str2) {
		return !StringUtils.equals(str1, str2);
	}

	/**
	 * URL编码</br>
	 * 
	 * @param str 需要编码的字符
	 * @return 编码后的内容
	 */
	public static String urlEncode(String str) {
		if (str == null) {
			return null;
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * URL解码</br>
	 * 
	 * @param str 需要解码的内容
	 * @return 解码后的内容
	 */
	public static String urlDecode(String str) {
		if (str == null) {
			return null;
		}
		try {
			return URLDecoder.decode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 在不满足指定位数的数字前面填充0
	 * 
	 * @param number 数字
	 * @param minlen 位数
	 * @return String
	 */
	public static String fixed(int number, int minlen) {
		String result = String.valueOf(number);
		while (result.length() < minlen) {
			result = "0" + result;
		}
		return result;
	}

	// public static int getTableIndex(String str) {
	// long hash = getHashCode(str.toLowerCase());
	// return (int) (hash % 1024);
	// }

	/**
	 * 返回字符串的hashcode
	 * 
	 * @param str 字符串
	 * @return 哈希值
	 */
	public static long getHashCode(String str) {
		int h = 0;
		char val[] = str.toCharArray();
		for (int i = 0; i < val.length; i++) {
			h = 31 * h + val[i];
		}
		return Math.abs((long) h);
	}

	/**
	 * 构造请求地址
	 * 
	 * @param url path
	 * @param params 参数对
	 * @return 生成的请求地址
	 */

	public static String createRequestUrl(String url, Map<String, Object> params) {

		String s = url;
		boolean firstParam = true;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if (firstParam) {
				s += "?";
				firstParam = false;
			}
			else {
				s += "&";
			}
			try {
				s += key + "=" + StringUtil.urlEncode(value);
			}
			catch (RuntimeException ue) {
				s += key + "=" + value;
			}
		}

		return s;
	}

	/**
	 * 统计字符出现次数.
	 * 
	 * @param content
	 * @param split
	 * @return
	 */
	public static int countString(String content, String split) {
		if (StringUtils.isEmpty(content)) {
			return 0;
		}
		int count = 0;
		int index = -1;
		while (true) {
			index = content.indexOf(split, index + 1);
			if (index == -1) {
				break;
			}
			count++;
		}
		return count;
	}

	// /**
	// * 获取字符串缩写
	// *
	// * @param len
	// * 长度
	// * @param content
	// * 内容
	// * @return
	// */
	// public static String getShortString(int length, String content) {
	// if (StringUtils.isEmpty(content)) {
	// return null;
	// }
	// String str = content;
	// String result = "";
	// int k = length;
	// for (int i = 0; i < str.length(); i++) {
	// if (k <= 0) {
	// break;
	// }
	// String temp = str.substring(i, i + 1);
	// int len = getBytes(temp);
	// if (len == 2) {
	// k = k - 2;
	// }
	// else {
	// k--;
	// }
	// result += temp;
	// }
	// if (k == 0) { // 表示名称已超出范围，则用...表示
	// result += "...";
	// }
	// return result;
	// }

	/**
	 * 获取字节数(一个中文相当于2个字节).
	 * 
	 * @param str 字符串
	 * @return 字节数
	 */
	public static int getBytes(String str) {
		if (StringUtils.isEmpty(str)) {
			return 0;
		}
		try {
			return str.getBytes("GBK").length;
		}
		catch (UnsupportedEncodingException e) {
			throw new NullPointerException("转换编码出错.");
		}
	}

	/**
	 * 获取字符串缩写
	 * 
	 * @param len 长度
	 * @param content 内容
	 * @return
	 */
	public static String getShortString(int length, String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		String str = content;
		StringBuilder result = new StringBuilder();
		int k = length;
		for (int i = 0; i < str.length(); i++) {
			if (k <= 0) {
				break;
			}
			String temp = str.substring(i, i + 1);
			int len = getBytes(temp);
			if (len == 2) {
				k = k - 2;
			}
			else {
				k--;
			}
			result.append(temp);
		}
		if (k == 0) { // 表示名称已超出范围，则用...表示
			result.append("...");
		}
		return result.toString();
	}

	public static String toIn(List<String> list) {
		AssertUtil.assertNotEmpty(list, "参数list不能为空.");

		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			str = StringUtil.escapeSQLParam(str);
			sb.append("'" + str + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String toIn(Set<String> set) {
		AssertUtil.assertNotEmpty(set, "参数set不能为空.");

		StringBuilder sb = new StringBuilder();
		for (String str : set) {
			str = StringUtil.escapeSQLParam(str);
			sb.append("'" + str + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 首字母变小写.
	 * 
	 * @param word
	 * @return
	 */
	public static String firstCharToLowerCase(String word) {
		if (word.length() == 1) {
			return word.toLowerCase();
		}
		char c = word.charAt(0);
		char newChar;
		if (c >= 'A' && c <= 'Z') {
			newChar = (char) (c + 32);
		}
		else {
			newChar = c;
		}
		return newChar + word.substring(1);
	}

	/**
	 * 首字母变大写.
	 * 
	 * @param word
	 * @return
	 */
	public static String firstCharToUpperCase(String word) {
		if (word.length() == 1) {
			return word.toUpperCase();
		}
		char c = word.charAt(0);
		char newChar;
		if (c >= 'a' && c <= 'z') {
			newChar = (char) (c - 32);
		}
		else {
			newChar = c;
		}
		return newChar + word.substring(1);
	}

	/**
	 * 用于MySQL全文检索中文编码.
	 * <p>
	 * 
	 * @param str
	 * @return
	 */
	public static String getIntString(final String str) {
		if (str == null) {
			return "";
		}
		byte[] bytes;
		try {
			bytes = str.toLowerCase().getBytes("GBK");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		StringBuilder sb = new StringBuilder();
		int iscn = 0;
		for (int i = 0; i < bytes.length; i++) {
			int j = bytes[i];
			if (bytes[i] < 0) {
				j = j * (-1);
				if (j < 10) {
					sb.append('0');
				}
				sb.append(j);
				iscn++;
				if (iscn == 2) {
					sb.append(' ');
					iscn = 0;
				}
			}
			else {
				byte[] b = new byte[] { bytes[i] };
				for (int n = 0; n < b.length; n++) {
					String str1 = "000" + b[n];
					str1 = str1.substring(str1.length() - 4);
					sb.append(str1).append(' ');
				}
			}
		}
		return sb.toString();
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}

	/**
	 * 判断是否为英文字母.
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isLetter(char c) {
		if (c >= 'a' && c <= 'z') {
			return true;
		}
		if (c >= 'A' && c <= 'Z') {
			return true;
		}
		return false;
	}

	public static List<String> splitToList(String str, String separatorChars) {
		if (str == null) {
			return null;
		}
		if (str.isEmpty()) {
			return new ArrayList<String>();
		}
		String[] strs = StringUtils.split(str, separatorChars);
		List<String> list = new ArrayList<String>();
		for (String s : strs) {
			list.add(s);
		}
		return list;
	}
}
