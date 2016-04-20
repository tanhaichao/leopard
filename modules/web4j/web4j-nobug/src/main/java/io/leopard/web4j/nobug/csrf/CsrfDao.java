package io.leopard.web4j.nobug.csrf;

public interface CsrfDao {

	// public static final String publicKey = "csrfTokenKeyxx123";

	void checkToken(String user, String token);
}
