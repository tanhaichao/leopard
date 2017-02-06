package io.leopard.jdbc.builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.lang.Paging;
import io.leopard.lang.TimeRange;

public class QueryBuilder {

	private String tableName;

	private String rangeFieldName;
	private TimeRange range;

	private String orderFieldName;
	// 按desc 还是asc
	private String orderDirection;
	private String groupbyFieldName;

	private Integer limitStart;
	private Integer limitSize;

	private Map<String, Object> whereMap = new LinkedHashMap<String, Object>();

	private List<String> whereExpressionList = new ArrayList<String>();

	private Map<String, String> likeMap = new LinkedHashMap<String, String>();

	public QueryBuilder(String tableName) {
		this.tableName = tableName;
	}

	public QueryBuilder range(String fieldName, TimeRange range) {
		if (range == null) {
			return this;
		}
		this.rangeFieldName = fieldName;
		this.range = range;
		return this;
	}

	public QueryBuilder addWhere(String expression) {
		whereExpressionList.add(expression);
		return this;
	}

	public QueryBuilder addString(String fieldName, String value) {
		return this.addString(fieldName, value, false);
	}

	public QueryBuilder addString(String fieldName, String value, boolean like) {
		if (StringUtils.hasLength(value)) {
			if (like) {
				this.addLike(fieldName, value);
			}
			else {
				this.addWhere(fieldName, value);
			}
		}
		return this;
	}

	public QueryBuilder addLong(String fieldName, long value) {
		if (value > 0) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public QueryBuilder addWhere(String fieldName, Object value) {
		whereMap.put(fieldName, value);
		return this;
	}

	public QueryBuilder addLike(String fieldName, String value) {
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("参数不能为空.");
		}
		value = value.replace("%", "");
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("参数不能包含特殊字符[" + value + "].");
		}
		likeMap.put(fieldName, value);
		return this;
	}

	public QueryBuilder order(String fieldName) {
		return this.order(fieldName, "desc");
	}

	public QueryBuilder order(String fieldName, String orderDirection) {
		this.orderFieldName = fieldName;
		this.orderDirection = orderDirection;
		return this;
	}

	public QueryBuilder groupby(String fieldName) {
		this.groupbyFieldName = fieldName;
		return this;
	}

	public QueryBuilder limit(int start, int size) {
		this.limitStart = start;
		this.limitSize = size;
		return this;
	}

	protected String getRangeSQL(StatementParameter param) {
		StringBuilder rangeSQL = new StringBuilder();
		if (this.range != null) {
			if (range.getStartTime() != null) {
				rangeSQL.append(this.rangeFieldName + ">=?");
				param.setDate(range.getStartTime());
			}

			if (range.getEndTime() != null) {
				if (rangeSQL.length() > 0) {
					rangeSQL.append(" and ");
				}
				rangeSQL.append(this.rangeFieldName + "<=?");
				param.setDate(range.getEndTime());
			}
		}

		return rangeSQL.toString();
	}

	protected String getWhereExpressionSQL() {
		if (this.whereExpressionList.isEmpty()) {
			return "";
		}
		StringBuilder whereSQL = new StringBuilder();
		for (String expression : this.whereExpressionList) {
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			whereSQL.append(expression);
		}
		return whereSQL.toString();
	}

	protected String getWhereSQL(StatementParameter param) {
		StringBuilder whereSQL = new StringBuilder();
		for (Entry<String, Object> entry : this.whereMap.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			if (value instanceof List) {
				@SuppressWarnings("rawtypes")
				List list = (List) value;
				String sql = this.getWhereInSql(fieldName, list);
				whereSQL.append(" ").append(sql);
			}
			else {
				whereSQL.append(fieldName).append("=?");
				param.setObject(value.getClass(), value);
			}
		}
		return whereSQL.toString();
	}

	protected String getWhereInSql(String fieldName, @SuppressWarnings("rawtypes") List list) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("list参数不能为空.");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(fieldName).append(" in (");
		for (Object obj : list) {
			String str = (String) obj;
			str = escapeSQLParam(str);
			sql.append("'" + str + "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	protected String getLikeSQL(StatementParameter param) {
		StringBuilder whereSQL = new StringBuilder();
		for (Entry<String, String> entry : this.likeMap.entrySet()) {
			String fieldName = entry.getKey();
			String value = entry.getValue();
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			whereSQL.append(fieldName).append(" like '%" + escapeSQLParam(value) + "%'");
		}

		return whereSQL.toString();
	}

	/**
	 * 对SQL语句进行转义
	 * 
	 * @param param SQL语句
	 * @return 转义后的字符串
	 */
	private static String escapeSQLParam(final String param) {
		int stringLength = param.length();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = param.charAt(i);
			switch (c) {
			case 0: /* Must be escaped for 'mysql' */
				buf.append('\\');
				buf.append('0');
				break;
			case '\n': /* Must be escaped for logs */
				buf.append('\\');
				buf.append('n');
				break;
			case '\r':
				buf.append('\\');
				buf.append('r');
				break;
			case '\\':
				buf.append('\\');
				buf.append('\\');
				break;
			case '\'':
				buf.append('\\');
				buf.append('\'');
				break;
			case '"': /* Better safe than sorry */
				buf.append('\\');
				buf.append('"');
				break;
			case '\032': /* This gives problems on Win32 */
				buf.append('\\');
				buf.append('Z');
				break;
			default:
				buf.append(c);
			}
		}
		return buf.toString();
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		StringBuilder sb = new StringBuilder();

		sb.append("select * from " + tableName);
		StringBuilder where = new StringBuilder();

		{
			String rangeSQL = this.getRangeSQL(param);
			if (rangeSQL.length() > 0) {
				where.append(rangeSQL);
			}
			{
				String whereSQL = this.getWhereSQL(param);
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
			{
				String whereSQL = this.getWhereExpressionSQL();
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
			{
				String whereSQL = this.getLikeSQL(param);
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
		}

		if (where.length() > 0) {
			sb.append(" where " + where.toString());
		}
		// System.err.println("groupbyFieldName:" + groupbyFieldName + " orderFieldName:" + orderFieldName);
		if (groupbyFieldName != null && groupbyFieldName.length() > 0) {
			sb.append(" group by " + groupbyFieldName);
		}
		if (orderFieldName != null && orderFieldName.length() > 0) {
			sb.append(" order by " + orderFieldName + " " + orderDirection);
		}
		sb.append(" limit ?,?");
		param.setInt(limitStart);
		param.setInt(limitSize);

		String sql = sb.toString();
		// System.err.println("sql:" + sql);
		return jdbc.queryForPaging(sql, elementType, param);

	}

}
