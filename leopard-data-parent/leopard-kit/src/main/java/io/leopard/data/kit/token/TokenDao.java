package io.leopard.data.kit.token;

public interface TokenDao {

	boolean add(Token token);

	Token last(String account, String category, String target);

	boolean updateUsed(String tokenId, boolean used);

}
