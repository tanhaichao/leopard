package io.leopard.data.kit.password;

import io.leopard.util.EncryptUtil;

public class PasswordUtil {

	public static String encryptPassword(String password, String salt) {
		String str = password + "@" + salt;
		return EncryptUtil.sha1(str).toLowerCase();
	}
}
