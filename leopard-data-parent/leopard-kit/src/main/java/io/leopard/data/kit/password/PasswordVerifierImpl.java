package io.leopard.data.kit.password;

import java.util.ArrayList;
import java.util.List;

import io.leopard.core.exception.forbidden.PasswordWrongException;

public class PasswordVerifierImpl implements PasswordVerifier {

	private PasswordVerifier[] passwordVerifiers;

	public PasswordVerifierImpl() {
		List<PasswordVerifier> list = new ArrayList<PasswordVerifier>();
		list.add(new Sha1PasswordVerifier());
		list.add(new Md5PasswordVerifier());

		passwordVerifiers = new PasswordVerifier[list.size()];
		list.toArray(passwordVerifiers);
	}

	@Override
	public boolean verify(String username, String password, String salt, String dbEncryptedPassword) {
		for (PasswordVerifier verifier : passwordVerifiers) {
			if (verifier.verify(username, password, salt, dbEncryptedPassword)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void check(String username, String password, String salt, String dbEncryptedPassword) throws PasswordWrongException {
		boolean correctPassword = this.verify(username, password, salt, dbEncryptedPassword);
		if (!correctPassword) {
			throw new PasswordWrongException("密码[" + username + "]不正确.");
		}
	}

	@Override
	public String makeToken(String dbEncryptedPassword) {
		return PassportTokenUtil.makeToken(dbEncryptedPassword);
	}

}
