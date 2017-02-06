package io.leopard.data.kit.token;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;

public class TokenDaoMysqlImpl implements TokenDao {

	private Jdbc jdbc;

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public Jdbc getJdbc() {
		return jdbc;
	}

	@Override
	public boolean add(Token token) {
		InsertBuilder builder = new InsertBuilder("token");
		builder.setString("tokenId", token.getTokenId());
		builder.setString("category", token.getCategory());
		builder.setString("target", token.getTarget());
		builder.setString("account", token.getAccount());
		builder.setString("token", token.getToken());
		builder.setBool("used", token.isUsed());
		builder.setDate("posttime", token.getPosttime());
		builder.setDate("expiryTime", token.getExpiryTime());
		builder.setDate("lmodify", token.getLmodify());
		return jdbc.insertForBoolean(builder);
	}

	@Override
	public Token last(String account, String category, String target) {
		String sql = "select * from token where account=? and category=? and target=? and used=0 order by posttime desc limit 1";
		return this.jdbc.query(sql, Token.class, account, category, target);
	}

	@Override
	public boolean updateUsed(String tokenId, boolean used) {
		String sql = "update token set used=? where tokenId=?";
		return this.jdbc.updateForBoolean(sql, used, tokenId);
	}
}
