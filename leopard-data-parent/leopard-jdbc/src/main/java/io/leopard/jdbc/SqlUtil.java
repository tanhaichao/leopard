package io.leopard.jdbc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SqlUtil {

	/**
	 * 获取拼接参数后的sql.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 拼接后的sql
	 */
	public static String getSQL(String sql, StatementParameter param) {
		int i = 0;
		while (sql.indexOf('?') > -1) {
			if (param == null) {
				throw new InvalidParamDataAccessException("没有设置参数.");
			}
			if (i >= param.size()) {
				return sql;
			}
			Class<?> type = param.getType(i);
			Object obj = param.getObject(i);
			String value = getValue(type, obj);
			sql = sql.substring(0, sql.indexOf('?')) + value + sql.substring(sql.indexOf('?') + 1, sql.length());
			i++;
		}
		return sql;//
	}

	protected static String getValue(Class<?> type, Object obj) {
		String value;
		if (type.equals(String.class)) {
			value = "'" + ((String) obj) + "'";
		}

		else if (type.equals(Date.class)) {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) obj);
			value = "'" + time + "'";
		}
		// else if (type.equals(OnlyDate.class)) {
		// value = "'" + ((OnlyDate) obj).toString() + "'";
		// }
		// else if (type.equals(Month.class)) {
		// value = "'" + ((Month) obj).toString() + "'";
		// }
		else if (type.equals(Integer.class)) {
			value = Integer.toString(((Integer) obj));
		}
		else if (type.equals(Boolean.class)) {
			value = (((Boolean) obj) ? 1 : 0) + "";
		}
		else if (type.equals(Float.class)) {
			value = Float.toString(((Float) obj));
		}
		else if (type.equals(Double.class)) {
			value = Double.toString(((Double) obj));
		}
		else if (type.equals(Long.class)) {
			value = Long.toString(((Long) obj));
		}
		else if (type.equals(byte[].class)) {
			value = "byte[]";
		}
		else {
			throw new InvalidParamDataAccessException("未知参数类型[" + type.getName() + "].");
		}
		return value;
	}

	/**
	 * 转换成select count(*)语句.
	 * 
	 * @param sql
	 * @return
	 */
	public static String toCountSql(String sql) {
		sql = sql.replaceAll("select .*? from", "select count(*) from");
		sql = sql.replaceAll("SELECT .*? FROM", "SELECT count(*) FROM");
		sql = sql.replaceAll(" LIMIT.*", "");
		sql = sql.replaceAll(" limit.*", "");
		return sql;
	}

	/**
	 * 对SQL语句进行转义
	 * 
	 * @param param SQL语句
	 * @return 转义后的字符串
	 */
	public static String escapeSQLParam(final String param) {
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

	public static String toIn(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			str = escapeSQLParam(str);
			sb.append("'" + str + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
