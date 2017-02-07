package io.leopard.web4j.nobug.csrf;

import javax.servlet.http.HttpServletRequest;

public interface CsrfDao {

	// public static final String publicKey = "csrfTokenKeyxx123";

	// void checkToken(String user, String token);

	/**
	 * 获取当前账号.
	 * 
	 * @return
	 */
	String getAccount(HttpServletRequest request);

	String encrypt(String str);
}
