package io.leopard.jdbc.builder;

import java.util.Date;

import org.springframework.util.StringUtils;

public class SelectBuilder extends AbstractSqlBuilder implements SqlBuilder {

	private String sql;

	private int start;
	private int size;

	private String where = "";

	/**
	 * 构造方法.
	 * 
	 * @param sql
	 *            select语句
	 */
	public SelectBuilder(String sql) {
		this(sql, -1, -1);
	}

	/**
	 * 构造方法.
	 * 
	 * @param sql
	 *            select语句
	 * @param size
	 *            返回的记录数
	 */
	public SelectBuilder(String sql, int size) {
		this(sql, -1, size);
	}

	/**
	 * 构造方法.
	 * 
	 * @param sql
	 *            select语句
	 * @param start
	 *            记录的开始位置
	 * @param size
	 *            返回的记录数
	 */
	public SelectBuilder(String sql, int start, int size) {
		this.sql = sql;
		this.start = start;
		this.size = size;
	}

	/**
	 * 设置Date类型参数的开始、结束时间.
	 * 
	 * @param fieldName
	 * @param startTime
	 * @param endTime
	 */
	public void setTimeRange(String fieldName, Date startTime, Date endTime) {
		this.where += " and " + fieldName + ">=? and " + fieldName + "<=?";
		this.getParam().setDate(startTime);
		this.getParam().setDate(endTime);
	}

	/**
	 * 设置Date参数的开始时间.
	 * 
	 * @param fieldName
	 * @param startTime
	 */
	public void setStartTime(String fieldName, Date startTime) {
		this.where += " and " + fieldName + ">=?";
		this.getParam().setDate(startTime);
	}

	/**
	 * 设置Date参数的结束时间.
	 * 
	 * @param fieldName
	 * @param endTime
	 */
	public void setEndTime(String fieldName, Date endTime) {
		this.where += " and " + fieldName + "<=?";
		this.getParam().setDate(endTime);
	}

	private boolean isInitSql = false;

	/**
	 * 返回sql语句.
	 */
	public String getSql() {
		if (!isInitSql) {
			this.initSql();
			this.isInitSql = true;
		}
		return sql;
	}

	/**
	 * 拼接select语句.
	 */
	protected void initSql() {
		String paramWhere = "";
		{

			// Object[] args = this.getParam().getArgs();
			for (String fieldName : fieldList) {
				paramWhere += " and " + fieldName + "=?";
			}
		}

		if (this.start > -1) {
			this.getParam().setInt(start);
		}
		if (this.size > -1) {
			this.getParam().setInt(size);
		}

		this.where = paramWhere + this.where;

		if (!StringUtils.isEmpty(this.where)) {
			String where = this.where.replaceFirst(" and ", " where ");
			this.sql = this.insert(where + " ");
		}
	}

	/**
	 * 添加查询条件到sql语句中.
	 * 
	 * @param where
	 *            查询条件
	 * @return
	 */
	protected String insert(String where) {
		String str = this.sql.toLowerCase();
		if (str.indexOf(" where ") != -1) {
			throw new IllegalArgumentException("语句不能包含where关键字.");
		}
		int index = str.indexOf("order by ");
		if (index == -1) {
			throw new RuntimeException("找不到order by语句.");
		}
		return sql.substring(0, index) + where + sql.substring(index);
	}
}
