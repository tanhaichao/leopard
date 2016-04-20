package io.leopard.commons.utility;

/**
 * 32进制算法.
 * 
 * @author 阿海
 * 
 */
public final class Digit32 {
	private Digit32() {

	}

	private static final int BIT = 32; //

	/**
	 * 32进制字符数组
	 */
	private static final char[] DIGITS = { //
	'A', 'B', 'C', 'D', 'E', 'F', 'G', //
			'H', 'I', 'J', 'K', 'L', 'M', 'N', //
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', //
			'V', 'W', 'X', 'Y', 'Z',//
			'0', '1', '2', '3', '4', '5' };

	/**
	 * 根据单个字符获取对应的数字
	 * 
	 */
	public static int getNum(final char single) {
		for (int n = 0; n < BIT; n++) {
			if (single == DIGITS[n]) {
				return n;
			}
		}
		return 0;
	}

	/**
	 * 10进制转成32进制</br>
	 * 
	 * @param num
	 *            需要转换的10进制数字
	 * @return 转换后的32进制字符
	 */
	public static String toString(final long num) {
		long newTimestamp = num;
		char[] buf = new char[BIT];
		int charPos = BIT;
		int radix = 1 << 5;
		long mask = radix - 1;
		do {
			buf[--charPos] = DIGITS[(int) (newTimestamp & mask)];
			newTimestamp >>>= 5;
		}
		while (newTimestamp != 0);
		return new String(buf, charPos, (BIT - charPos));
	}

	/**
	 * 10进制转成32进制(不足len位，前面用A(0)补全)</br>
	 * 
	 * @param num
	 *            需要转换的10进制数字
	 * @param len
	 *            长度
	 * @return 转换后的32进制字符
	 */
	public static String toString(final long num, final int len) {
		String result = toString(num);
		while (result.length() < len) {
			result = "A" + result;
		}
		return result;
	}

	/**
	 * 32进制转10进制</br>
	 * 
	 * @param src
	 *            需要转换的字符
	 * @return 转后的10进制数
	 */
	public static long toDecimal(final String src) {
		long result = 0;
		int len = src.length();
		int num = len;
		int b = 0;
		for (int i = 0; i < len; i++) {
			b = getNum(src.charAt(i));
			result = result + b * (long) Math.pow(BIT, (num - 1));
			num--;
		}
		return result;
	}

	// public static void main(final String[] args) {
	// String time = Digit32.toString(System.currentTimeMillis());
	// System.out.println("time:" + time);
	//
	// }

}
