package io.leopard.commons.utility;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.ArrayUtils;

public class AesEcbUtil {

	private final static String algorithm = "AES";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static byte[] decodeBase64(String key) throws Exception {
		byte[] result = org.apache.commons.codec.binary.Base64.decodeBase64(key);
		return result;
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static String encodeBase64(byte[] key) {
		String result = new String(org.apache.commons.codec.binary.Base64.encodeBase64(key, true));
		return result;
		// return (new BASE64Encoder()).encodeBuffer(key);
	}

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
		return AESUtil.byte2hex(raw);
	}

	protected static void checkKey(String key) {
		if (key == null || key.length() == 0) {
			throw new IllegalArgumentException("key不能为空.");
		}
		if (key.length() != 16) {
			throw new IllegalArgumentException("key的长度只能为16位.");
		}
	}

	protected static byte[] filled16MultipleBytes(byte[] bytes) {
		int _length = bytes.length % 16;
		byte[] bytes2 = ArrayUtils.addAll(bytes, new byte[16 - _length]);
		return bytes2;
	}

	// protected static String encrypt(byte[] data, String key) {
	// byte[] rawKey = AESUtil.hex2byte(key);
	// // Instantiate the cipher
	// try {
	// SecretKeySpec skeySpec = new SecretKeySpec(rawKey, algorithm);
	// Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
	// cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	//
	// byte[] encrypted = cipher.doFinal(data);
	// return encodeBase64(encrypted);
	// }
	// catch (Exception e) {
	// throw new RuntimeException(e.getMessage(), e);
	// }
	//
	// }

	/**
	 * 解密
	 * 
	 * @param encrypted
	 * @param rawKey
	 * @return
	 */
	public static String decrypt(String encrypted, String key) {
		checkKey(key);
		byte[] rawKey = key.getBytes();

		try {
			byte[] tmp = decodeBase64(encrypted);
			SecretKeySpec skeySpec = new SecretKeySpec(rawKey, algorithm);
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(tmp);
			String result = new String(decrypted);
			// result = result.replace(((char) 0) + "", "");
			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param rawKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) {
		checkKey(key);
		byte[] rawKey = key.getBytes();
		// Instantiate the cipher
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(rawKey, algorithm);
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] bytes = data.getBytes();
			bytes = filled16MultipleBytes(bytes);
			byte[] encrypted = cipher.doFinal(bytes);
			return encodeBase64(encrypted);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
