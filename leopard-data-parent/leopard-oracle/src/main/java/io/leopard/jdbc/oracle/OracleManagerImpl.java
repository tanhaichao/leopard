package io.leopard.jdbc.oracle;

import java.util.List;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.oracle.model.UserTableComment;

public class OracleManagerImpl implements OracleManager {

	private Jdbc jdbc;

	public OracleManagerImpl(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public List<UserTableComment> listUserTableComments() {
		String sql = "select * from user_tab_comments";
		return jdbc.queryForList(sql, UserTableComment.class);
	}

}
