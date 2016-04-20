package io.leopard.data.signature;

import java.util.Date;

/**
 * 签名算法.
 * 
 * @author 阿海
 * 
 */
public interface Signature {

	String encode(String user, Date posttime);

	SignatureInfo decode(String encodeString);

}
