package io.leopard.commons.utility;

/**
 * 字符串16进制转换
 * 
 * @author 阿海
 * 
 */
public class Base16 {

	/**
	 * 将字符串转换成 16进制字符串</br>
	 * 
	 * @param s
	 *            字符串
	 * @return 转换后的16进制字符串
	 */
	public static String encode(String str) {
		StringBuilder sb = new StringBuilder();
		char[] arr = str.toCharArray();
		for (char c : arr) {
			String s4 = Integer.toHexString(c);
			sb.append(s4);
		}
		return sb.toString();
	}

	/**
	 * 将 16进制字符串转换成字符串,不支持中文。</br>
	 * 
	 * @param s
	 *            16进制字符串
	 * @return 转换后的字符串
	 */
	public static String decode(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
		}
		return new String(baKeyword);// UTF-16le:Not
	}

	// public static void main(String args[]) {
	// // System.out.println(Base16.encode("abcdefg中国人"));
	// // System.out.println(Base16.decode("616263646566674e2d56fd4eba"));
	// }
}
