package io.leopard.jdbc.builder;

import io.leopard.jdbc.StatementParameter;

import java.util.LinkedList;
import java.util.List;

public class CompositeQueryBuilder {

	/**
	 * 忽略NULL
	 */
	private boolean ignoreNull = false;

	private String sql = "";

	private StatementParameter statementParameter = new StatementParameter();

	public CompositeQueryBuilder(String sql, boolean ignoreNull) {
		this.sql = sql;
		this.ignoreNull = ignoreNull;
	}

	public CompositeQueryBuilder(String sql) {
		this.sql = sql;
	}

	public StatementParameter getStatementParameter() {
		return statementParameter;
	}

	public void setStatementParameter(StatementParameter statementParameter) {
		this.statementParameter = statementParameter;
	}

	public void addParameter(String field, String operate, Object value) {
		V v = new V();
		v.setField(field);
		v.setOperate(operate);
		v.setValue(value);
		paramList.add(v);
	}

	public String getSql() {
		String sql = this.sql;
		if (!paramList.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			for (V v : paramList) {
				sb.append(this.getWhere(v));
				if (!this.checkValueNull(v.getValue())) {
					statementParameter.setObject(v.getValue().getClass(), v.getValue());
				}
			}
			String whereStr = sb.toString();
			int i = whereStr.lastIndexOf("and");
			if (i != -1) {
				whereStr = whereStr.substring(0, i);
				sql += " where " + whereStr;
			}
			paramList.clear();
		}
		return sql;
	}

	/**
	 * 检查 value是否为空
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkValueNull(Object value) {
		return value == null;
	}

	/**
	 * 根据参数获取where条件
	 * 
	 * @param v
	 * @return
	 */
	private String getWhere(V v) {
		String sql = "";
		if (this.checkValueNull(v.getValue())) {
			if (this.ignoreNull == false) {
				sql = "(" + v.getField() + " is null ) and ";
			}
		}
		else {
			sql = "(" + v.getField() + " " + v.getOperate() + " ?) and ";
		}
		return sql;
	}

	private List<V> paramList = new LinkedList<V>();

	private static class V {

		public String getOperate() {
			return operate;
		}

		public void setOperate(String operate) {
			this.operate = operate;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		private String operate;
		private Object value;
		private String field;
	}

}
