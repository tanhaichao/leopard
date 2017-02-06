package io.leopard.data.kit.password;

import org.apache.commons.lang.NotImplementedException;

import io.leopard.core.exception.forbidden.PasswordWrongException;
import io.leopard.util.EncryptUtil;

public class Md5PasswordVerifier implements PasswordVerifier {

	@Override
	public boolean verify(String username, String password, String salt, String dbEncryptedPassword) {
		String encryptedPassword;
		if (password.length() == 32) {
			encryptedPassword = password;
		}
		else {
			String str = password + "@" + salt;
			encryptedPassword = EncryptUtil.md5(str).toLowerCase();
		}
		return encryptedPassword.equalsIgnoreCase(dbEncryptedPassword);
	}

	@Override
	public void check(String username, String password, String salt, String dbPassword) throws PasswordWrongException {
		throw new NotImplementedException();
	}

	@Override
	public String makeToken(String dbEncryptedPassword) {
		throw new NotImplementedException();
	}

}
