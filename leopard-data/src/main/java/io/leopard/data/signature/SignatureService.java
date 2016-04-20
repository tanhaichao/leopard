package io.leopard.data.signature;


public interface SignatureService {

	/**
	 * 
	 * @param user
	 * @param encodeString
	 * @return
	 */
	SignatureInfo checkSignature(String user, String encodeString);

	String encode(String user);

	SignatureInfo checkSignature(Long yyuid, String key);
}
