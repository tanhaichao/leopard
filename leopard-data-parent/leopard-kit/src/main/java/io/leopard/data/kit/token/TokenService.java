package io.leopard.data.kit.token;

import java.util.Date;

import io.leopard.core.exception.forbidden.TokenWrongException;

public interface TokenService {

	String add(String account, String category, String target, String token);

	Token last(String account, String category, String target);

	boolean updateUsed(String tokenId, boolean used);

	String makeToken(String account, String category, String target);

	Token check(String account, String category, String target, String token) throws TokenWrongException;

	String add(String account, String category, String target, String token, Date expiryTime);

	String makeToken(String account, String category, String target, Date expiryTime);

}
