package io.leopard.data.alldb;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;

public class MysqlImpl {
	private Jdbc jdbc;
	private String tableName;

	private String[] keys;

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	public <T> T get(Class<T> elementType, Object... values) {
		StringBuilder sb = new StringBuilder("select * from ").append(tableName).append(" where ");
		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			sb.append(keys[i]).append("=?");
		}
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < keys.length; i++) {
			param.setObject(values[i].getClass(), values[i]);
		}
		String sql = sb.toString();
		return jdbc.query(sql, elementType, param);
	}
}
