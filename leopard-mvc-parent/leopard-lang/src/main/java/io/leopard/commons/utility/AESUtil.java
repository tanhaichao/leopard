package io.leopard.commons.utility;

import io.leopard.burrow.lang.AssertUtil;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AES加密,解密
 * 
 * @author 阿海
 */
public class AESUtil {
	private static final Log logger = LogFactory.getLog(AESUtil.class);

	/**
	 * 生成AES key</br>
	 * 
	 * @return String, 生成的Key
	 */
	public static String makeKey() {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
		}
		catch (NoSuchAlgorithmException e) {
			// logger.error(e.getMessage(), e);
			// return null;
			throw new RuntimeException(e.getMessage(), e);
		}
		kgen.init(128); // 192 and 256 bits may not be available
		// Generate the secret key specs.
		SecretKey key = kgen.generateKey();
		byte[] raw = key.getEncoded();
		String content = byte2hex(raw);
		return content;
	}

	/**
	 * 根据公匙获取私匙</br>
	 * 
	 * @param publickey
	 *            ,公匙
	 * @return String，私钥
	 */
	protected static Key getKey(String publickey) {
		// publickey = "1556272FD0F3D48E1923240296A6F869";
		AssertUtil.assertNotEmpty(publickey, "publickey不能为空.");
		if (publickey.length() != 32) {
			throw new IllegalArgumentException("publickey必须为32位.");
		}
		// logger.info("publickey:" + publickey);
		byte[] bytes = hex2byte(publickey);
		return new SecretKeySpec(bytes, "AES");
	}

	/**
	 * 用公钥对需要加密的内容进行AES加密</br>
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param publickey
	 *            公钥
	 * @return String，加密后的密文
	 */
	public static String encrypt(String content, String publickey) {
		Key key = getKey(publickey);
		try {
			Cipher cp = Cipher.getInstance("AES");
			cp.init(Cipher.ENCRYPT_MODE, key);

			byte[] ptext = content.getBytes();
			byte[] ctext = cp.doFinal(ptext);

			return byte2hex(ctext);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 用密码对需要解密的内容进行AES解密</br>
	 * 
	 * @param content
	 *            需要解密的内容
	 * @param password
	 *            密码
	 * @return String，解密后的明文
	 */
	public static String decrypt(String content, String password) {
		Key key = getKey(password);
		try {
			Cipher cp = Cipher.getInstance("AES");
			cp.init(Cipher.DECRYPT_MODE, key); // 初始化
			byte[] ptext = cp.doFinal(hex2byte(content)); // 解密
			return new String(ptext); // 重新显示明文
		}
		catch (Exception e) {
			// logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将16进制字符串转成字节数组</br>
	 * 
	 * @param strhex
	 *            字符串
	 * @return byte[] 字符数组
	 */
	protected static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 != 0) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	/**
	 * 字节数组转成16进制字符串</br>
	 * 
	 * @param b
	 *            字节数组
	 * @return String，字符串
	 */
	protected static String byte2hex(byte b[]) {
		StringBuilder sb = new StringBuilder();
		for (int n = 0; n < b.length; n++) {
			String stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				sb.append("0");
			}
			sb.append(stmp);
		}
		return sb.toString().toUpperCase();
	}

}
