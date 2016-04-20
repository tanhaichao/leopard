package io.leopard.burrow.util;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	/**
	 * md5加密</br>
	 * 
	 * @praram str 需要进行md5加密的字符
	 * @return 已进行md5的加密的字符
	 */
	public static String md5(String str) {
		String md5 = encode(str, "MD5").toLowerCase();
		return md5;
	}

	/**
	 * sha1 加密
	 * 
	 * @praram str 需要进行sha1加密的字符
	 * @return 已进行sha1的加密的字符
	 */
	public static String sha1(String str) {
		String sha1 = encode(str, "SHA-1");
		return sha1;
	}

	/**
	 * 按类型对字符串进行加密并转换成16进制输出</br>
	 * 
	 * @param str
	 *            字符串
	 * @param type
	 *            可加密类型md5, des , sha1
	 * @return 加密后的字符串
	 */
	private static String encode(String str, String type) {
		try {
			MessageDigest alga = MessageDigest.getInstance(type);
			alga.update(str.getBytes());
			byte digesta[] = alga.digest();
			String hex = byte2hex(digesta);
			// String hex2 = byte2hex2(digesta);
			// if (!hex.equals(hex2)) {
			// System.out.println("str:" + str);
			// }
			return hex;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将字节数组转换成16进制字符
	 * 
	 * @param b
	 *            需要转换的字节数组
	 * @return 转换后的16进制字符
	 */
	private static String byte2hex(byte b[]) {
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < b.length; n++) {
			String stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				// hs = hs + "0" + stmp;
				sb.append("0");
			}
			sb.append(stmp);
			// else {
			// // hs = hs + stmp;
			// }
		}

		return sb.toString().toUpperCase();
	}

	/**
	 * 将成16进制转换字符串字符</br>
	 * 
	 * @param s
	 *            需要转换的16进制字符串 return 转换后的字符
	 */
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 将字符串转换成16进制字符</br>
	 * 
	 * @param s
	 *            需要转换的字符串 return 转换后的16进制字符
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			// try {
			baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			// }
			// catch (Exception e) {
			// e.printStackTrace();
			// }
		}
		// try {
		return new String(baKeyword);// UTF-16le:Not
		// }
		// catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// return s;
	}

	/**
	 * 获取HmacSHA1签名 </br>
	 * 
	 * @param data
	 *            数据
	 * @param key
	 *            密钥
	 * @return 签名后的字符
	 * @throws Exception
	 *             签名失败
	 */
	public static String hmacSHA1(String data, String key) {
		StringBuilder sb = new StringBuilder();
		try {
			byte[] keyBytes = key.getBytes();
			SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			for (byte b : rawHmac) {
				sb.append(byteToHexString(b));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("获取HmacSHA1签名失败[" + e.getMessage() + "]!", e);
		}
		return sb.toString();
	}

	/**
	 * 将字节转换成16进制字符
	 * 
	 * @param ib
	 *            需要转换的字节
	 * @return 转换后的16进制字符
	 */
	protected static String byteToHexString(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0f];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

}
