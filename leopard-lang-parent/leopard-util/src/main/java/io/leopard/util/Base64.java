package io.leopard.util;

public class Base64 {

	/**
	 * 对字符串进行base64解码</br>
	 * 
	 * @param content
	 *            字符串
	 * @return 解码后的字符串
	 */
	public static String decode(String content) {

		// return content;
		if (content == null || content.length() == 0) {
			return content;
		}
		else {
			byte[] decodeQueryString = org.apache.commons.codec.binary.Base64.decodeBase64(content);
			return new String(decodeQueryString);
		}

	}

	/**
	 * 对字符串进行Base64编码</br>
	 * 
	 * @param content
	 *            字符串
	 * @return 编码后的字符串
	 */
	public static String encode(String content) {
		if (content == null || content.length() == 0) {
			return content;
		}
		return org.apache.commons.codec.binary.Base64.encodeBase64String(content.getBytes()).trim();

	}

	// public static void main(String[] args) {
	// String str = "你好";
	// String encode = Base64.encode(str);
	// encode =
	// "ZXlKNWVYVnBaQ0k2TXpFeE1UY3pOVEF3TENKMWMyVnlibUZ0WlNJNkltRTNORFV4TXpJd2RHbGhiaUlzSW5CdmMzUjBhVzFsSWpvaQ0KTWpBeE1TMHhNQzB4T1NBeE5qb3hOem94T1M0d0luMD0NCg==";
	// String decode = Base64.decode(encode);
	// // System.out.println(encode);
	// System.out.println(decode);
	// }
}
