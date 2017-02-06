package io.leopard.burrow.util.bak;

import io.leopard.burrow.util.StringUtil;

//import org.springframework.web.util.JavaScriptUtils;

public class JstlFunctions {

	// private static final Log logger = LogFactory.getLog(JstlFunctions.class);

	// /**
	// * 对javascript文本中的特殊字符进行转义
	// *
	// * @param str
	// * javascript文本
	// * @return 转义后的字符串
	// */
	// public static String escapeJavascript(String str) {
	// return JavaScriptUtils.javaScriptEscape(str);
	// }
	//
	// /**
	// * 对javascript参数中的特殊字符进行转义
	// *
	// * @param str
	// * javascript参数
	// * @return 转义后的字符串
	// */
	// public static String escapeJavascriptParam(String str) {
	// if (str == null) {
	// return null;
	// }
	// if (str.indexOf('"') != -1) {
	// Exception e = new Exception("invalid js param:" + str);
	// logger.error(e.getMessage(), e);
	// str = str.replace("\"", "");
	// }
	// return JavaScriptUtils.javaScriptEscape(str);
	// }

	/**
	 * 对html标签进行转义
	 * 
	 * @param str
	 *            html标签
	 * @return 转义后的字符串
	 */
	public static String escapeHTMLTags(final String str) {
		return StringUtil.escapeHTMLTags(str);
	}

	/**
	 * 对字符串进行URL编码
	 * 
	 * @param str
	 *            字符串
	 * @return 编码后的字符串
	 */
	public static String urlEncode(final String str) {
		return StringUtil.urlEncode(str);
	}
}
