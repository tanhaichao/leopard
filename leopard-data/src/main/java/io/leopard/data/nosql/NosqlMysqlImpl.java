package io.leopard.data.nosql;

import io.leopard.burrow.lang.ContextImpl;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;

import org.springframework.stereotype.Repository;

@Repository
public class NosqlMysqlImpl extends ContextImpl implements Nosql {

	private Jdbc jdbc;

	private String tableName;

	public Jdbc jdbc() {
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

	@Override
	public String getString(String tableName, String fieldName, Field... keys) {
		StringBuilder sb = new StringBuilder("select ");
		sb.append(fieldName).append(" from ").append(tableName).append(" where ");
		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			sb.append(keys[i].getName()).append("=?");
		}
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i].getValue();
			param.setObject(value.getClass(), value);
		}
		String sql = sb.toString();
		return jdbc.queryForString(sql, param);
	}

	@Override
	public <T> T get(String tableName, Class<T> elementType, String key1, String value1) {
		return this.get(tableName, elementType, new Field(key1, value1));
	}

	@Override
	public <T> T get(String tableName, Class<T> elementType, Field... keys) {
		StringBuilder sb = new StringBuilder("select * from ").append(tableName).append(" where ");
		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			sb.append(keys[i].getName()).append("=?");
		}
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i].getValue();
			param.setObject(value.getClass(), value);
		}
		String sql = sb.toString();
		return jdbc.query(sql, elementType, param);
	}

	@Override
	public boolean delete(String tableName, Field... keys) {
		StringBuilder sb = new StringBuilder("delete from ").append(tableName).append(" where ");
		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			sb.append(keys[i].getName()).append("=?");
		}
		StatementParameter param = new StatementParameter();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i].getValue();
			param.setObject(value.getClass(), value);
		}
		String sql = sb.toString();
		return jdbc.updateForBoolean(sql, param);
	}

	@Override
	public boolean delete(String tableName, String key1, String value1) {
		return this.delete(tableName, new Field(key1, value1));
	}

	@Override
	public <T> T get(Class<T> elementType, Field... keys) {
		return this.get(tableName, elementType, keys);
	}

	@Override
	public <T> T get(Class<T> elementType, String key1, String value1) {
		return this.get(tableName, elementType, key1, value1);
	}

	@Override
	public boolean delete(Field... keys) {
		return this.delete(tableName, keys);
	}

	@Override
	public boolean delete(String key1, String value1) {
		return this.delete(value1, key1, value1);
	}

}
