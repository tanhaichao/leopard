package io.leopard.jdbc.builder;

import io.leopard.jdbc.StatementParameter;

public class UpdateBuilder extends AbstractSqlBuilder implements SqlBuilder {
	private String tableName;

	public final WhereBuilder where = new WhereBuilder();

	/**
	 * 
	 * @param tableName
	 *            表名称.
	 */
	public UpdateBuilder(String tableName) {
		this.tableName = tableName;
	}

	private boolean isMergedParam = false;// 参数是否已经合并.

	@Override
	public StatementParameter getParam() {
		if (!isMergedParam) {
			StatementParameter param = where.getParam();
			int size = param.size();
			for (int i = 0; i < size; i++) {
				Class<?> type = param.getType(i);
				Object value = param.getObject(i);
				statementParameter.setObject(type, value);
			}
		}
		isMergedParam = true;
		return statementParameter;
	}

	private String parsedSql = null;

	@Override
	public String getSql() {
		if (parsedSql != null) {
			return parsedSql;
		}
		if (fieldList.isEmpty()) {
			throw new NullPointerException("还没有设置任何参数.");
		}

		StringBuilder sb = new StringBuilder("UPDATE ");
		sb.append(tableName).append(" SET ");
		int count = 0;
		for (String filedName : fieldList) {
			if (count > 0) {
				sb.append(", ");
			}
			sb.append(filedName).append("=?");
			count++;
		}

		sb.append(" WHERE ").append(where.getSql()).append(";");
		parsedSql = sb.toString();
		return parsedSql;
	}
}
